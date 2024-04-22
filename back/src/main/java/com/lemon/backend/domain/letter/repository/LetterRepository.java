package com.lemon.backend.domain.letter.repository;

import com.lemon.backend.domain.letter.entity.Letter;
import com.lemon.backend.domain.letter.repository.custom.LetterRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterRepository extends JpaRepository<Letter, Long>, LetterRepositoryCustom {
}
