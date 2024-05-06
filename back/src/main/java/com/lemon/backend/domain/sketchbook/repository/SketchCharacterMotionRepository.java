package com.lemon.backend.domain.sketchbook.repository;

import com.lemon.backend.domain.sketchbook.entity.Sketchbook;
import com.lemon.backend.domain.sketchbook.entity.SketchbookCharacterMotion;
import com.lemon.backend.domain.sketchbook.repository.custom.SketchbookRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SketchCharacterMotionRepository extends JpaRepository<SketchbookCharacterMotion, Long> {
//    Optional<SketchbookCharacterMotion> findByCharacterMotionAndSketchbook(Long sketchbookId, Long characterMotionId);
}

