package com.lemon.backend.domain.sketchbook.service;

import com.lemon.backend.domain.sketchbook.dto.requestDto.SketchbookCreateDto;
import com.lemon.backend.domain.sketchbook.dto.requestDto.SketchbookUpdateDto;
import com.lemon.backend.domain.sketchbook.dto.responseDto.SketchbookGetDto;
import com.lemon.backend.domain.sketchbook.dto.responseDto.SketchbookGetSimpleDto;
import jakarta.transaction.Transactional;

import java.util.List;

public interface SketchbookService {
    List<SketchbookGetSimpleDto> getSketchList(Integer userId);

    SketchbookGetDto getSketchSelect(Long sketchId);

    @Transactional
    Long createSketchbook(SketchbookCreateDto sketchDto);

    @Transactional
    boolean changePublic(Long sketchbookId);

    @Transactional
    Long updateSketchbook(Long sketchbookId, SketchbookUpdateDto sketchDto);

    @Transactional
    Long ShareSketchbook(Long sketchbookId, SketchbookUpdateDto sketchDto);

    @Transactional
    void deleteSketchbook(Long sketchbookId);
}
