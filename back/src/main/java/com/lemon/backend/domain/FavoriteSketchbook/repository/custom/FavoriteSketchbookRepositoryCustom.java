package com.lemon.backend.domain.FavoriteSketchbook.repository.custom;

import com.lemon.backend.domain.FavoriteSketchbook.dto.FavoriteSketchbookGetDto;

import java.util.List;
import java.util.Optional;

public interface FavoriteSketchbookRepositoryCustom {
    Optional<List<FavoriteSketchbookGetDto>> findByUserId(Integer userId);
}
