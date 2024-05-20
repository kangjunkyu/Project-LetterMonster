package com.lemon.backend.domain.FavoriteSketchbook.service;

import com.lemon.backend.domain.FavoriteSketchbook.dto.FavoriteSketchbookGetDto;
import com.lemon.backend.domain.FavoriteSketchbook.entity.FavoriteSketchbook;
import com.lemon.backend.domain.sketchbook.dto.responseDto.SketchbookGetFromFavoriteDto;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface FavoriteSketchbookService {
    String addFavoriteSketchbook(Integer userId, Long sketchbookId);

    void deleteFavotieSketchbook(Integer userId, Long sketchbookId);

    @Transactional
    String toggleFavoriteSketchbook(Integer userId, Long sketchbookId);

    Optional<List<FavoriteSketchbookGetDto>> getFavoriteSketchbooksByUser(Integer userId);

    List<SketchbookGetFromFavoriteDto> getFromFavoriteDtos(Integer userId);

    boolean checkFavorite(Integer userId, Long sketchbookId);
}
