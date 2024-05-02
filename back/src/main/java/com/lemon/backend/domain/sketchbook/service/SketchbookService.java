package com.lemon.backend.domain.sketchbook.service;

import com.lemon.backend.domain.sketchbook.dto.requestDto.SketchbookCreateDto;
import com.lemon.backend.domain.sketchbook.dto.requestDto.SketchbookUpdateDto;
import com.lemon.backend.domain.sketchbook.dto.responseDto.*;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SketchbookService {
    List<SketchbookGetSimpleDto> getSketchList(Integer userId);

    SketchbookGetDto getSketchSelect(String sketchId);

    SketchbookGetDetailDto getSketchSelect2(String sketchId);

    Optional<List<SketchbookSearchGetDto>> searchSkechbook(String searchName);

    @Transactional
    Long createSketchbook(Integer userId, SketchbookCreateDto sketchDto);

    @Transactional
    boolean changePublic(Long sketchbookId);

    @Transactional
    Long updateSketchbook(Long sketchbookId, SketchbookUpdateDto sketchDto);

    @Transactional
    Long ShareSketchbook(Long sketchbookId, SketchbookUpdateDto sketchDto);

    @Transactional
    void deleteSketchbook(Long sketchbookId);

    SketchbookDetailPageDto getSketchSelect3(String sketchId, Pageable pageable);

    List<SketchbookGetAllDto> getSketchAll();

    @Transactional
    void changeRepresent(Integer userId, Long newRepresentId);
}
