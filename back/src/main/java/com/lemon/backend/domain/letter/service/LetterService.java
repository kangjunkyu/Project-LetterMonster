package com.lemon.backend.domain.letter.service;

import com.lemon.backend.domain.letter.dto.requestDto.LetterGetListDto;
import com.lemon.backend.domain.letter.dto.responseDto.LetterCreateDto;
import jakarta.transaction.Transactional;

import java.util.List;

public interface LetterService {
    List<LetterGetListDto> getLetterList(Long sketchbookId);

    @Transactional
    Long createLetter(Integer senderId, LetterCreateDto letterDto);

    @Transactional
    void deleteLetter(Long letterId);
}
