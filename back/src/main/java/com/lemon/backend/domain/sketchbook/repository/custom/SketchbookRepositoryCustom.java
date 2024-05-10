package com.lemon.backend.domain.sketchbook.repository.custom;

import com.lemon.backend.domain.sketchbook.dto.responseDto.*;
import com.lemon.backend.domain.sketchbook.entity.Sketchbook;
import com.lemon.backend.domain.sketchbook.entity.SketchbookCharacterMotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SketchbookRepositoryCustom {
    Optional<List<SketchbookGetSimpleDto>> getSketchList(Integer userId);

    Optional<List<SketchbookGetSimpleDto>> getFriendSketchList(Integer userId);

    Optional<SketchbookGetDetailDto> getSketchSelect(String sketchId);

    Optional<SketchbookCharacterMotion> findByCharacterMotionAndSketchbook(Long sketchbookId, Long characterMotionId);

    Optional<SketchbookGetDetailDto> getSketchSelect2(Integer userId, String sketchId);

    SketchbookDetailPageDto getSketchSelect3(String sketchId, Pageable pageable);

    Optional<String> findHighestSketchbookTagByName(String name);

    Optional<List<SketchbookGetAllDto>> getSketchAll();

    Optional<List<SketchbookSearchGetDto>> searchList(String sketchbookName);

    Optional<Sketchbook> findRepresentSkechbook(Integer userId);

    boolean existsRepresentSketchbook(Integer userId);

}
