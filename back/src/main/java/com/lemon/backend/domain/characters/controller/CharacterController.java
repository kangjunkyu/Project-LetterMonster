package com.lemon.backend.domain.characters.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.lemon.backend.domain.characters.dto.response.*;
import com.lemon.backend.domain.characters.service.CharacterService;
import com.lemon.backend.domain.characters.service.impl.CharacterServiceImpl;
import com.lemon.backend.global.exception.CustomException;
import com.lemon.backend.global.exception.ErrorCode;
import com.lemon.backend.global.response.SuccessCode;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static com.lemon.backend.global.response.CommonResponseEntity.getResponseEntity;

@RestController
@RequestMapping("/characters")

public class CharacterController {
    private final CharacterService characterService;
    private final Bucket bucket;


    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;

        Bandwidth limit = Bandwidth.classic(10000, Refill.intervally(10000, Duration.ofSeconds(10)));
        this.bucket = Bucket.builder().addLimit(limit).build();

    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> makeCharacter(Authentication authentication, @RequestParam("file")MultipartFile file, @RequestParam("nickname")String nickname) {
        Integer userId = (Integer) authentication.getPrincipal();
        Long characterId = characterService.createCharacter(file, userId, nickname);
        return getResponseEntity(SuccessCode.CREATED, characterId);
    }

    //비회원 캐릭터 생성
    @PostMapping("/public/create")
    public ResponseEntity<?> makeCharacter(@RequestParam("file")MultipartFile file, @RequestParam("nickname")String nickname) {
        Long characterId = characterService.createCharacter(file, null, nickname);
        return getResponseEntity(SuccessCode.CREATED, characterId);
    }

    @DeleteMapping("/public/cancel")
    public ResponseEntity<?> cancelMakeCharacter(@RequestParam(name="characterId") Long characterId) {
        characterService.cancelMakeCharacter(characterId);
        return getResponseEntity(SuccessCode.OK, null);
    }

    @PatchMapping("/modify/nickname")
    public ResponseEntity<?> modifyCharacterNickname(@RequestParam(name="characterId") Long characterId, @RequestParam(name="nickname") String nickname) {
        characterService.updateCharacterNickname(characterId, nickname);
        return getResponseEntity(SuccessCode.OK, null);
    }

    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> showCharacters(Authentication authentication) {
        Integer userId = (Integer) authentication.getPrincipal();
        List<CharactersGetDto> charactersGetDtoList = characterService.showCharacters(userId);
        return getResponseEntity(SuccessCode.OK, charactersGetDtoList);
    }

    @PatchMapping("/my/maincharacter")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> changeMainCharacter(Authentication authentication, @RequestParam(name="characterId") Long characterId) {
        Integer userId = (Integer) authentication.getPrincipal();
        characterService.changeMainCharacter(characterId, userId);
        return getResponseEntity(SuccessCode.OK, null);
    }
    @GetMapping("/public/list/motion")
    public ResponseEntity<?> showMotions() {
        List<RepresentMotionDto> representMotionDtoList = characterService.showMotions();
        return getResponseEntity(SuccessCode.OK, representMotionDtoList);
    }

    // 선택한 캐릭터 모션에 따른 gif 주소를 반환한다.
    @GetMapping("/public/select/motion")
    public ResponseEntity<?> selectCharacterMotion(@RequestParam(name="characterId") Long characterId, @RequestParam(name="motionId") Long motionId) {
        if(bucket.tryConsume(1)) {
            SelectCharacterMotionDto selectCharacterMotionDto = characterService.selectCharacterMotion(characterId, motionId);
            return getResponseEntity(SuccessCode.OK, selectCharacterMotionDto);
        }

        throw new CustomException(ErrorCode.TOO_MANY_REQUEST);
    }

    // 캐릭터와 관련 캐릭터모션을 전부 삭제한다.
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCharacter(@RequestParam(name="characterId") Long characterId) {
        characterService.deleteCharacter(characterId);
        return getResponseEntity(SuccessCode.OK, null);
    }

    @GetMapping("/receive")
    public ResponseEntity<?> showChracterMotionsByUsers(Authentication authentication){
        Integer userId = (Integer) authentication.getPrincipal();
        Optional<List<CharacterMotionSketchbookProjection>> list = characterService.findCharacterMotionByUsers(userId);
        return getResponseEntity(SuccessCode.OK, list);
    }

    @GetMapping("/mine")
    public ResponseEntity<?> showCharacterMotionsBySelf(Authentication authentication){
        Integer userId = (Integer) authentication.getPrincipal();
        Optional<List<CharacterMotionProjection>> list = characterService.findCharacterMotionBySelf(userId);
        return getResponseEntity(SuccessCode.OK, list);
    }

    @GetMapping("/public/{characterId}")
    public ResponseEntity<?> showCharacter(@PathVariable Long characterId) {
        Optional<CharacterInfoDto> list = characterService.findCharacterByUser(characterId);
        return getResponseEntity(SuccessCode.OK, list);
    }
}
