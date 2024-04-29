package com.lemon.backend.domain.characters.repository;

import com.lemon.backend.domain.characters.entity.CharacterMotion;
import com.lemon.backend.domain.characters.entity.Characters;
import com.lemon.backend.domain.characters.entity.Motion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterMotionRepository extends JpaRepository<CharacterMotion, Long> {
    Optional<CharacterMotion> findByCharactersAndMotion(Characters characters, Motion motion);

    List<CharacterMotion> findAllByCharacters(Characters characters);
}
