package com.lemon.backend.domain.characters.repository;

import com.lemon.backend.domain.characters.entity.Characters;
import com.lemon.backend.domain.characters.entity.Motion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotionRepository extends JpaRepository<Motion, Long> {
}
