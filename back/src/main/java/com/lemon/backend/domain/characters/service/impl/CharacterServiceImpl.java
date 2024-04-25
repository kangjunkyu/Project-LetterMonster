package com.lemon.backend.domain.characters.service.impl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.lemon.backend.domain.characters.dto.response.CharactersGetDto;
import com.lemon.backend.domain.characters.dto.response.CharactersIdDto;
import com.lemon.backend.domain.characters.dto.response.ImageUrlDto;
import com.lemon.backend.domain.characters.dto.response.RepresentMotionDto;
import com.lemon.backend.domain.characters.entity.CharacterMotion;
import com.lemon.backend.domain.characters.entity.Characters;
import com.lemon.backend.domain.characters.entity.Motion;
import com.lemon.backend.domain.characters.repository.CharacterMotionRepository;
import com.lemon.backend.domain.characters.repository.CharacterRepository;
import com.lemon.backend.domain.characters.repository.MotionRepository;
import com.lemon.backend.domain.characters.service.CharacterService;
import com.lemon.backend.domain.users.user.entity.Users;
import com.lemon.backend.domain.users.user.repository.UserRepository;
import com.lemon.backend.global.exception.CustomException;
import com.lemon.backend.global.exception.ErrorCode;
import org.springframework.http.HttpHeaders;
import io.lettuce.core.KillArgs;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.representer.Represent;


import java.io.IOException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;
    private final CharacterMotionRepository characterMotionRepository;
    private final MotionRepository motionRepository;
    private final UserRepository userRepository;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${FAST_API.URL}")
    private String fastApiUrl;
    /*
    1. react에서 파일 업로드 or 사용자 그리기를 통해 생성된 그림 파일을 spring boot로 전송한다.
    2. spring boot에서 원본을 s3에 저장한다.
    3. url과 캐릭터 이름을 db에 저장한다
     */
    @Override
    @Transactional
    public Long createCharacter(MultipartFile file, int userId, String nickname) {
        try {
            Optional<Users> optionalUsers = userRepository.findById(userId);

            if(optionalUsers.isPresent()) {
                Users users = optionalUsers.get();
                Characters characters = Characters.builder().nickname(nickname).users(users).build();
                characterRepository.save(characters);
                String fileName = characters.getId().toString() + ".png";
                ObjectMetadata metadata= new ObjectMetadata();
                metadata.setContentType(file.getContentType());
                metadata.setContentLength(file.getSize());
                amazonS3Client.putObject(bucket,fileName,file.getInputStream(),metadata);
                String fileUrl = amazonS3Client.getUrl(bucket, fileName).toString();
                characters.changeUrl(fileUrl);
                return characters.getId();
            } else {
                throw new CustomException(ErrorCode.USERS_NOT_FOUND);
            }


        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.CHARACTER_SAVE_FAILED);
        }

    }

    /*
    캐릭터의 닉네임을 사용자의 입력에 따라 변경한다.
     */
    @Override
    @Transactional
    public void updateCharacterNickname(Long characterId, String nickname) {
        Optional<Characters> optionalCharacters = characterRepository.findById(characterId);

        if(optionalCharacters.isPresent()) {
            Characters characters = optionalCharacters.get();
            characters.changeNickname(nickname);
        } else {
            throw new CustomException(ErrorCode.CHARACTER_NOT_FOUND);
        }

    }

    /*
    캐릭터 정보를 확인하고, S3에 해당하는 이름의 모션이 있는지 확인한다.
    만약 S3에 해당하는 캐릭터모션이 있다면 gif 링크를 반환하고,
    없다면 fastapi로 모션 이름과 캐릭터 원본 url을 전송하여 gif를 반환받는다.
    이후 캐릭터 모션을 DB에 저장한다.
     */
    @Override
    public String selectCharacterMotion(Long characterId, Long motionId) {
        Optional<Characters> optionalCharacters = characterRepository.findById(characterId);
        Optional<Motion> optionalMotion = motionRepository.findById(motionId);

        if(optionalCharacters.isEmpty()) throw new CustomException(ErrorCode.CHARACTER_NOT_FOUND);
        if(optionalMotion.isEmpty()) throw new CustomException(ErrorCode.MOTION_NOT_FOUND);

        Characters characters = optionalCharacters.get();
        Motion motion = optionalMotion.get();
        Optional<CharacterMotion> optionalCharacterMotion = characterMotionRepository.findByCharactersAndMotion(characters, motion);
        if(optionalCharacterMotion.isPresent()) {
            CharacterMotion characterMotion = optionalCharacterMotion.get();
            return characterMotion.getUrl();
        } else {
            // Rest template
            RestTemplate restTemplate = new RestTemplate();
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("character_id", characterId.toString());

            requestBody.put("motion_name", motion.getName());
            requestBody.put("img_url", characters.getId()+".png");

            // HttpEntity에 헤더와 요청 데이터 적용
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

            // POST 요청 보내기
            ResponseEntity<Map> response = restTemplate.exchange(fastApiUrl+"/ai/character/create", HttpMethod.POST, entity, Map.class);

            // 응답으로 받은 데이터 처리
            if (response.getStatusCode().is2xxSuccessful()) {
                Map<String, String> responseBody = response.getBody();
                return responseBody.getOrDefault("gif_path", "No GIF path returned");
            } else {
                return "Failed to create GIF";
            }

        }
    }

    /*
    사용자가 가진 정적 캐릭터 리스트를 반환한다.
     */
    @Override
    public List<CharactersGetDto> showCharacters(int userId) {
        Optional<Users> optionalUser = userRepository.findByIdFetch(userId);
        if(optionalUser.isPresent()) {
            Users user = optionalUser.get();
            List<Characters> characterList = user.getCharacterList();
            List<CharactersGetDto> charactersGetDtoList = new ArrayList<>();
            for(Characters c: characterList) {
                charactersGetDtoList.add(CharactersGetDto.builder().nickname(c.getNickname()).mainCharacter(c.getMainCharacter()).imageUrl(c.getUrl()).characterId(c.getId()).build());
            }
            return charactersGetDtoList;
        } else {
            throw new CustomException(ErrorCode.USERS_NOT_FOUND);
        }

    }

    /*
    캐릭터를 선택하면, 미리 저장되어 있는 대표 모션 gif들과 각각의 모션id를 반환한다.
     */
    @Override
    public List<RepresentMotionDto> showMotions() {
        Optional<Characters> optionalCharacters = characterRepository.findById(1L);
        if(optionalCharacters.isEmpty()) throw new CustomException(ErrorCode.CHARACTER_NOT_FOUND);

        List<CharacterMotion> characterMotionList = characterMotionRepository.findAllByCharacters(optionalCharacters.get());

        List<RepresentMotionDto> representMotionDtoList = new ArrayList<>();
        for(CharacterMotion cm : characterMotionList) {
            RepresentMotionDto representMotionDto = RepresentMotionDto.builder().motionId(cm.getMotion().getId()).name(cm.getMotion().getName()).imageUrl(cm.getUrl()).build();
            representMotionDtoList.add(representMotionDto);
        }
        return representMotionDtoList;
    }

    /*
    사용자가 가진 캐릭터중 대표캐릭터로 선정된 캐릭의 대표 여부를 false로, 선택한 캐릭터의 대표 여부를 true로 변경한다.
     */
    @Override
    public void changeMainCharacter(Long characterId, int userId) {
        Optional<Users> optionalUsers = userRepository.findByIdFetch(userId);

        if(optionalUsers.isEmpty()) throw new CustomException(ErrorCode.USERS_NOT_FOUND);

        Users user = optionalUsers.get();
        for(Characters c : user.getCharacterList()) {
            if(c.getMainCharacter()) c.changeMainCharacter();
            if(c.getId()==characterId) c.changeMainCharacter();
        }

    }

    /*
    캐릭터 생성을 취소하고, 사용자 그림파일을 s3에서 삭제한다. 또한 db에서 캐릭터를 삭제한다.
     */
    @Override
    @Transactional
    public void cancelMakeCharacter(Long characterId) {
        amazonS3Client.deleteObject(bucket, characterId.toString()+".png");
        characterRepository.deleteById(characterId);
    }

    /*
    사용자 그림파일을 s3에서 삭제한다. 또한 db에서 캐릭터의 url을 비우고 유저id를 null으로 만든다.
     */
    @Override
    @Transactional
    public void deleteCharacter(Long characterId) {

        Optional<Characters> optionalCharacters = characterRepository.findById(characterId);
        if(optionalCharacters.isEmpty()) throw new CustomException(ErrorCode.CHARACTER_NOT_FOUND);

        amazonS3Client.deleteObject(bucket, characterId.toString()+".png");
        Characters characters = optionalCharacters.get();
        characters.deleteUrl();
        characters.deleteUser();

    }
}
