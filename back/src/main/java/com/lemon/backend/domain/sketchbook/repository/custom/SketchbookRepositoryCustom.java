package com.lemon.backend.domain.sketchbook.repository.custom;

import com.lemon.backend.domain.sketchbook.dto.responseDto.*;
import com.lemon.backend.domain.sketchbook.entity.SketchbookCharacterMotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SketchbookRepositoryCustom {
    Optional<List<SketchbookGetSimpleDto>> getSketchList(Integer userId);

    Optional<List<SketchbookGetAllDto>> getSketchAll();

    Optional<SketchbookGetDto> getSketchSelect(String sketchId);

    Optional<SketchbookCharacterMotion> findByCharacterMotionAndSketchbook(Long sketchbookId, Long characterMotionId);

    Optional<SketchbookGetDetailDto> getSketchSelect2(String sketchId);

    SketchbookDetailPageDto getSketchSelect3(String sketchId, Pageable pageable);

    Optional<String> findHighestSketchbookTagByName(String name);
}
