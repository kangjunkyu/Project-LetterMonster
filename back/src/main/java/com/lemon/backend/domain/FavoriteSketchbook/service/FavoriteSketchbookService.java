package com.lemon.backend.domain.FavoriteSketchbook.service;

import com.lemon.backend.domain.FavoriteSketchbook.dto.FavoriteSketchbookGetDto;
import com.lemon.backend.domain.FavoriteSketchbook.entity.FavoriteSketchbook;

import java.util.List;
import java.util.Optional;

public interface FavoriteSketchbookService {
    String addFavoriteSketchbook(Integer userId, Long sketchbookId);

    void deleteFavotieSketchbook(Integer userId, Long favoriteId);

    Optional<List<FavoriteSketchbookGetDto>> getFavoriteSketchbooksByUser(Integer userId);
}
