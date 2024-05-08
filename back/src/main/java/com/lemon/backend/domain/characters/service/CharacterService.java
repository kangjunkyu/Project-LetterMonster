package com.lemon.backend.domain.characters.service;

import com.lemon.backend.domain.characters.dto.response.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public interface CharacterService {
    Long createCharacter(MultipartFile file, Integer userId, String nickname);

    void deleteCharacter(Long characterId);

    void updateCharacterNickname(Long characterId, String nickname);

    SelectCharacterMotionDto selectCharacterMotion(Long characterId, Long motionId);

    List<CharactersGetDto> showCharacters(int userId);

    List<RepresentMotionDto> showMotions();

    void changeMainCharacter(Long characterId, int userId);

    void cancelMakeCharacter(Long characterId);

    Optional<List<CharacterMotionSketchbookProjection>> findCharacterMotionByUsers(Integer userId);

    Optional<List<CharacterMotionProjection>> findCharacterMotionBySelf(Integer userId);

    Optional<CharacterInfoDto> findCharacterByUser(Long characterId);
}
