package com.lemon.backend.domain.FavoriteSketchbook.service.impl;

import com.lemon.backend.domain.FavoriteSketchbook.dto.FavoriteSketchbookGetDto;
import com.lemon.backend.domain.FavoriteSketchbook.entity.FavoriteSketchbook;
import com.lemon.backend.domain.FavoriteSketchbook.repository.FavoriteSketchbookRepository;
import com.lemon.backend.domain.FavoriteSketchbook.service.FavoriteSketchbookService;
import com.lemon.backend.domain.sketchbook.dto.responseDto.SketchbookGetFromFavoriteDto;
import com.lemon.backend.domain.sketchbook.entity.Sketchbook;
import com.lemon.backend.domain.sketchbook.repository.SketchbookRepository;
import com.lemon.backend.domain.users.user.entity.Users;
import com.lemon.backend.domain.users.user.repository.UserRepository;
import com.lemon.backend.global.exception.CustomException;
import com.lemon.backend.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class FavoriteSketchbookServiceImpl implements FavoriteSketchbookService {

    private final FavoriteSketchbookRepository favoriteSketchbookRepository;
    private final SketchbookRepository sketchbookRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public String addFavoriteSketchbook(Integer userId, Long sketchbookId){
        Users user = userRepository.findById(userId).orElseThrow(()-> new CustomException(ErrorCode.USERS_NOT_FOUND));
        Sketchbook sketchbook = sketchbookRepository.findById(sketchbookId).orElseThrow(() -> new CustomException(ErrorCode.SKETCHBOOK_NOT_FOUND));

        // 즐겨찾기 중복 확인
        boolean exists = favoriteSketchbookRepository.existsByUserAndSketchbook(user, sketchbook);
        if (exists) {
            throw new CustomException(ErrorCode.ALREADY_FAVORITE_SKECHBOOK); // 적절한 에러 코드를 정의하거나 메시지 처리
        }

        FavoriteSketchbook favoriteSketchbook = FavoriteSketchbook.builder()
                .user(user)
                .sketchbook(sketchbook)
                .build();
        favoriteSketchbookRepository.save(favoriteSketchbook);

        return favoriteSketchbook.getSketchbook().getName();
    }

    @Override
    @Transactional
    public void deleteFavotieSketchbook(Integer userId, Long sketchbookId){
        FavoriteSketchbook favoriteSketchbook = favoriteSketchbookRepository.findFavoriteSketchbook(sketchbookId);

        if(favoriteSketchbook == null){
            throw new CustomException(ErrorCode.SKETCHBOOK_NOT_FOUND);
        }

        if (!favoriteSketchbook.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.INVALID_ACCESS); // 사용자가 소유하지 않은 즐겨찾기를 삭제하려 할 때
        }

        favoriteSketchbookRepository.delete(favoriteSketchbook);
    }

    @Override
    @Transactional
    public String toggleFavoriteSketchbook(Integer userId, Long sketchbookId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));
        Sketchbook sketchbook = sketchbookRepository.findById(sketchbookId).orElseThrow(() -> new CustomException(ErrorCode.SKETCHBOOK_NOT_FOUND));

        FavoriteSketchbook favoriteSketchbook = favoriteSketchbookRepository.findByUserAndSketchbook(user, sketchbook).orElse(null);

        if (favoriteSketchbook != null) {
            favoriteSketchbookRepository.delete(favoriteSketchbook);
            return "Removed '" + sketchbook.getName() + "' from your favorites.";
        } else {
            FavoriteSketchbook newFavorite = FavoriteSketchbook.builder()
                    .user(user)
                    .sketchbook(sketchbook)
                    .build();
            favoriteSketchbookRepository.save(newFavorite);
            return "Added '" + sketchbook.getName() + "' to your favorites.";
        }
    }


    @Override
    public Optional<List<FavoriteSketchbookGetDto>> getFavoriteSketchbooksByUser(Integer userId) {
        return favoriteSketchbookRepository.findByUserId(userId);
    }

    @Override
    public List<SketchbookGetFromFavoriteDto> getFromFavoriteDtos(Integer userId){
        return favoriteSketchbookRepository.findByUserId2(userId);
    }

    @Override
    public boolean checkFavorite(Integer userId, Long sketchbookId) {
        return favoriteSketchbookRepository.checkFavoriteSketchbook(userId, sketchbookId);
    }

}
