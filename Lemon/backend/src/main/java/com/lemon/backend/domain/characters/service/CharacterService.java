package com.lemon.backend.domain.characters.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface CharacterService {
    Object createCharacter(MultipartFile file, String nickname);

    Object deleteCharacter(Long characterId);

    Object updateCharacterNickname(Long characterId);

    Object selectCharacterMotion(Long characterId);

    Object showCharacters(int userId);

    Object showMotions();

    Object changeMainCharacter(Long characterId);
}
