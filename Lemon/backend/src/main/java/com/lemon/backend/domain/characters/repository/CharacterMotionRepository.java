package com.lemon.backend.domain.characters.repository;

import com.lemon.backend.domain.characters.entity.CharacterMotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterMotionRepository extends JpaRepository<CharacterMotion, Long> {
}
