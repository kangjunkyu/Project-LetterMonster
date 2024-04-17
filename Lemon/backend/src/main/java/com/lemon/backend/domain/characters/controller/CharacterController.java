package com.lemon.backend.domain.characters.controller;

import com.lemon.backend.domain.characters.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterController {
    private final CharacterService characterService;
    @PostMapping("/create")
    public ResponseEntity<Object> makeCharacter(@RequestParam("file")MultipartFile file, @RequestParam("nickname")String nickname) {
        return ResponseEntity.ok()
                .body(characterService.createCharacter(file, nickname));
    }

    @DeleteMapping("/cancel/{characterId}")
    public ResponseEntity<Object> cancelMakeCharacter(@PathVariable(name="characterId") Long characterId) {
        return ResponseEntity.ok()
                .body(characterService.deleteCharacter(characterId));
    }

    @PatchMapping("/modify/nickname/{characterId}")
    public ResponseEntity<Object> modifyCharacterNickname(@PathVariable(name="characterId") Long characterId) {
        return ResponseEntity.ok().body(characterService.updateCharacterNickname(characterId));
    }

    @GetMapping("/list")
    public ResponseEntity<Object> showCharacters() {
        int userId = 0;
        return ResponseEntity.ok().body(characterService.showCharacters(userId));
    }

    @PatchMapping("/my/maincharacter/{characterId}")
    public ResponseEntity<Object> changeMainCharacter(@PathVariable(name="characterId") Long characterId) {
        int userId = 0;
        return ResponseEntity.ok().body(characterService.changeMainCharacter(characterId, userId));
    }
    @GetMapping("/list/motion")
    public ResponseEntity<Object> showMotions() {
        return ResponseEntity.ok().body(characterService.showMotions());
    }

    // 선택한 캐릭터 모션에 따른 gif 주소를 반환한다.
    @GetMapping("/select/motion")
    public ResponseEntity<Object> selectCharacterMotion(@RequestParam(name="characterId") Long characterId, @RequestParam(name="motionId") Long motionId) {
        return ResponseEntity.ok().body(characterService.selectCharacterMotion(characterId, motionId));
    }

    // 캐릭터와 관련 캐릭터모션을 전부 삭제한다.
    @DeleteMapping("/delete/{characterId}")
    public ResponseEntity<Object> deleteChracter(@PathVariable(name="characterId") Long characterId) {
        return ResponseEntity.ok().body(characterService.deleteCharacter(characterId));
    }
}
