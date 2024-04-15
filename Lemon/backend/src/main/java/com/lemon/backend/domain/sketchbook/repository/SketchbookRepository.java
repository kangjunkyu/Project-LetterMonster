package com.lemon.backend.domain.sketchbook.repository;

import com.lemon.backend.domain.sketchbook.entity.Sketchbook;
import com.lemon.backend.domain.sketchbook.repository.custom.SketchbookRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SketchbookRepository extends JpaRepository<Sketchbook, Integer>, SketchbookRepositoryCustom {
}
