package com.lemon.backend.domain.characters.service.impl;

import com.lemon.backend.domain.characters.entity.Characters;
import com.lemon.backend.domain.characters.repository.CharacterMotionRepository;
import com.lemon.backend.domain.characters.repository.CharacterRepository;
import com.lemon.backend.domain.characters.repository.MotionRepository;
import com.lemon.backend.domain.characters.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {
    
    private final CharacterRepository characterRepository;
    private final CharacterMotionRepository characterMotionRepository;
    private final MotionRepository motionRepository;

    /*
    1. react에서 파일 업로드 or 사용자 그리기를 통해 생성된 그림 파일을 spring boot로 전송한다.
    2. spring boot에서 원본을 s3에 저장한다.
    3. url과 캐릭터 이름을 db에 저장한다
     */
    @Override
    public Object createCharacter(MultipartFile file, String nickname) {

        return null;
    }

    /*
    캐릭터 생성을 취소하고, 사용자 그림파일을 s3에서 삭제한다. 또한 db에서 캐릭터를 삭제한다
     */
    @Override
    public Object deleteCharacter(Long characterId) {
        return null;
    }

    /*
    캐릭터의 닉네임을 사용자의 입력에 따라 변경한다.
     */
    @Override
    public Object updateCharacterNickname(Long characterId) {
        Optional<Characters> characters = characterRepository.findById(characterId);
        return null;
    }

    /*
    캐릭터 정보를 확인하고, S3에 해당하는 이름의 모션이 있는지 확인한다.
    만약 S3에 해당하는 모션이 있다면 gif 링크를 반환하고,
    없다면 fastapi로 모션 이름과 캐릭터 원본 url을 전송하여 gif를 반환받는다.
     */
    @Override
    public Object selectCharacterMotion(Long characterId, Long motionId) {
        return null;
    }

    /*
    사용자가 가진 정적 캐릭터 리스트를 반환한다.
     */
    @Override
    public Object showCharacters(int userId) {

        return null;
    }

    /*
    캐릭터를 선택하면, 미리 저장되어 있는 대표 모션 gif들과 각각의 모션id를 반환한다.
     */
    @Override
    public Object showMotions() {
        return null;
    }

    /*
    사용자가 가진 캐릭터중 대표캐릭터로 선정된 캐릭의 대표 여부를 false로, 선택한 캐릭터의 대표 여부를 true로 변경한다.
     */
    @Override
    public Object changeMainCharacter(Long characterId, int userId) {
        return null;
    }
}
