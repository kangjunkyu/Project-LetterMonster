package com.lemon.backend.domain.characters.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface CharacterService {
    Object createCharacter(MultipartFile file, int userId, String nickname);

    Object deleteCharacter(Long characterId);

    Object updateCharacterNickname(Long characterId, String nickname);

    Object selectCharacterMotion(Long characterId, Long motionId);

    Object showCharacters(int userId);

    Object showMotions();

    Object changeMainCharacter(Long characterId, int userId);

    Object cancelMakeCharacter(Long characterId);
}
