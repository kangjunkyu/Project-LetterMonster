package com.lemon.backend.domain.FavoriteSketchbook.repository.custom;

import com.lemon.backend.domain.FavoriteSketchbook.dto.FavoriteSketchbookGetDto;
import com.lemon.backend.domain.FavoriteSketchbook.entity.FavoriteSketchbook;
import com.lemon.backend.domain.sketchbook.dto.responseDto.SketchbookGetFromFavoriteDto;

import java.util.List;
import java.util.Optional;

public interface FavoriteSketchbookRepositoryCustom {
    Optional<List<FavoriteSketchbookGetDto>> findByUserId(Integer userId);

    List<SketchbookGetFromFavoriteDto> findByUserId2(Integer userId);

    FavoriteSketchbook findFavoriteSketchbook(Long sketchbookId);

    Boolean checkFavoriteSketchbook(Integer userId, Long sketchbookId);
}
