package com.lemon.backend.domain.letter.service;

import com.lemon.backend.domain.letter.dto.requestDto.LetterGetListDto;
import com.lemon.backend.domain.letter.dto.requestDto.LetterGetRecentListDto;
import com.lemon.backend.domain.letter.dto.requestDto.LetterCreateDto;
import com.lemon.backend.domain.letter.dto.responseDto.LetterCreateResponse;
import jakarta.transaction.Transactional;

import java.util.List;

public interface LetterService {
    List<LetterGetListDto> getLetterList(Long sketchbookId);

    List<LetterGetRecentListDto> getLetterThree(Integer userId);

    @Transactional
    Long createLetter(Integer senderId, LetterCreateDto letterDto);

    @Transactional
    void deleteLetter(Long letterId);

    @Transactional
    LetterCreateResponse createAnonymousLetter(LetterCreateDto letterDto);
}
