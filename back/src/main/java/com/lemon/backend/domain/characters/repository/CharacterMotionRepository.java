package com.lemon.backend.domain.characters.repository;

import com.lemon.backend.domain.characters.dto.response.CharacterInfoDto;
import com.lemon.backend.domain.characters.dto.response.CharacterMotionProjection;
import com.lemon.backend.domain.characters.dto.response.CharacterMotionSketchbookProjection;
import com.lemon.backend.domain.characters.entity.CharacterMotion;
import com.lemon.backend.domain.characters.entity.Characters;
import com.lemon.backend.domain.characters.entity.Motion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterMotionRepository extends JpaRepository<CharacterMotion, Long> {
    Optional<CharacterMotion> findByCharactersAndMotion(Characters characters, Motion motion);

    List<CharacterMotion> findAllByCharacters(Characters characters);

    @Query(value = "SELECT DISTINCT cm.character_motion_id AS id, cm.image_url AS imageUrl " +
            "FROM users u " +
            "JOIN sketchbook s ON u.users_id = s.users_id " +
            "JOIN sketchbook_character_motion scm ON s.sketchbook_id = scm.sketchbook_id " +
            "JOIN character_motion cm ON scm.character_motion_id = cm.character_motion_id " +
            "WHERE u.users_id = :userId", nativeQuery = true)
    Optional<List<CharacterMotionSketchbookProjection>> findDistinctCharacterMotionsByUserId(Integer userId);

    @Query(value = "SELECT MIN(cm.character_motion_id) AS id, cm.image_url AS imageUrl, m.name AS motionName, ch.nickname AS characterNickname " +
            "FROM character_motion cm " +
            "JOIN characters ch ON cm.characters_id = ch.characters_id " +
            "JOIN motion m ON cm.motion_id = m.motion_id " +
            "WHERE ch.users_id = :userId " +
            "GROUP BY cm.image_url, m.name, ch.nickname", nativeQuery = true)
    Optional<List<CharacterMotionProjection>> findDistinctCharacterMotionsByUserIdOnlySelf(Integer userId);

}

