package com.lemon.backend.domain.characters.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.lemon.backend.domain.characters.dto.response.*;
import com.lemon.backend.domain.characters.service.CharacterService;
import com.lemon.backend.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static com.lemon.backend.global.response.CommonResponseEntity.getResponseEntity;

@RestController
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterController {
    private final CharacterService characterService;
    private final AmazonS3Client amazonS3Client;
    @PostMapping("/create")
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
    public ResponseEntity<?> showCharacters(Authentication authentication) {
        Integer userId = (Integer) authentication.getPrincipal();
        List<CharactersGetDto> charactersGetDtoList = characterService.showCharacters(userId);
        return getResponseEntity(SuccessCode.OK, charactersGetDtoList);
    }

    @PatchMapping("/my/maincharacter")
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
        SelectCharacterMotionDto selectCharacterMotionDto = characterService.selectCharacterMotion(characterId, motionId);
        return getResponseEntity(SuccessCode.OK, selectCharacterMotionDto);
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

    @GetMapping("/{characterId}")
    public ResponseEntity<?> showCharacter(@PathVariable Long characterId) {
        Optional<CharacterInfoDto> list = characterService.findCharacterByUser(characterId);
        return getResponseEntity(SuccessCode.OK, list);
    }
}
