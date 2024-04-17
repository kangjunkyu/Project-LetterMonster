package com.lemon.backend.domain.sketchbook.repository.custom;

import com.lemon.backend.domain.sketchbook.dto.responseDto.SketchbookGetDetailDto;
import com.lemon.backend.domain.sketchbook.dto.responseDto.SketchbookGetDto;
import com.lemon.backend.domain.sketchbook.dto.responseDto.SketchbookGetSimpleDto;

import java.util.List;
import java.util.Optional;

public interface SketchbookRepositoryCustom {
    Optional<List<SketchbookGetSimpleDto>> getSketchList(Integer userId);

    Optional<SketchbookGetDto> getSketchSelect(Long sketchId);

    Optional<SketchbookGetDetailDto> getSketchSelect2(Long sketchId);
}
