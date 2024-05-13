package com.lemon.backend.domain.FavoriteSketchbook.controller;

import com.lemon.backend.domain.FavoriteSketchbook.dto.FavoriteRequestDto;
import com.lemon.backend.domain.FavoriteSketchbook.dto.FavoriteSketchbookGetDto;
import com.lemon.backend.domain.FavoriteSketchbook.entity.FavoriteSketchbook;
import com.lemon.backend.domain.FavoriteSketchbook.service.FavoriteSketchbookService;
import com.lemon.backend.domain.sketchbook.dto.responseDto.SketchbookGetFromFavoriteDto;
import com.lemon.backend.global.exception.CustomException;
import com.lemon.backend.global.exception.ErrorCode;
import com.lemon.backend.global.response.SuccessCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.lemon.backend.global.response.CommonResponseEntity.getResponseEntity;

@Tag(name = "FavoriteSketchbook 컨트롤러", description = "FavoriteSketchbook Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteSketchbookController {

    private final FavoriteSketchbookService favoriteSketchbookService;

    @GetMapping("/notuse")
    public ResponseEntity<?> findAll(Authentication authentication) {
        if (authentication.getPrincipal() instanceof Integer) {
            Integer loginId = (Integer) authentication.getPrincipal();
            Optional<List<FavoriteSketchbookGetDto>> list = favoriteSketchbookService.getFavoriteSketchbooksByUser(loginId);
            return getResponseEntity(SuccessCode.OK, list);
        } else {
            throw new CustomException(ErrorCode.INVALID_ACCESS);
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll2(Authentication authentication) {
        if (authentication.getPrincipal() instanceof Integer) {
            Integer loginId = (Integer) authentication.getPrincipal();
            List<SketchbookGetFromFavoriteDto> list = favoriteSketchbookService.getFromFavoriteDtos(loginId);
            return getResponseEntity(SuccessCode.OK, list);
        } else {
            throw new CustomException(ErrorCode.INVALID_ACCESS);
        }
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkFavorite(Authentication authentication, @RequestParam Long sketchbookId) {
        if (authentication.getPrincipal() instanceof Integer) {
            Integer loginId = (Integer) authentication.getPrincipal();
            boolean isFavorite = favoriteSketchbookService.checkFavorite(loginId, sketchbookId);
            return getResponseEntity(SuccessCode.OK, isFavorite);
        } else {
            throw new CustomException(ErrorCode.INVALID_ACCESS);
        }
    }

//    @PostMapping
//    public ResponseEntity<?> addFavorite(Authentication authentication, @RequestParam(value = "sketchbookId") Long sketchbookId) {
//        if (authentication.getPrincipal() instanceof Integer) {
//            Integer loginId = (Integer) authentication.getPrincipal();
//
//            String favorite = favoriteSketchbookService.addFavoriteSketchbook(loginId, sketchbookId);
//            return getResponseEntity(SuccessCode.OK, favorite);
//        } else {
//            throw new CustomException(ErrorCode.INVALID_ACCESS);
//        }
//    }

    @DeleteMapping
    public ResponseEntity<?> deleteFavorite(Authentication authentication, @RequestParam(value = "sketchbookId") Long sketchbookId) {
        if (authentication.getPrincipal() instanceof Integer) {
            Integer loginId = (Integer) authentication.getPrincipal();
            favoriteSketchbookService.deleteFavotieSketchbook(loginId, sketchbookId);
            return getResponseEntity(SuccessCode.OK);
        } else {
            throw new CustomException(ErrorCode.INVALID_ACCESS);
        }
    }

//    @PostMapping
//    public ResponseEntity<?> handleFavoriteSketchbook(Authentication authentication, @RequestBody FavoriteRequestDto favoriteRequestDto) {
//        if (authentication.getPrincipal() instanceof Integer) {
//            Integer loginId = (Integer) authentication.getPrincipal();
//            try {
//                if (favoriteRequestDto.getAction().equals("add")) {
//                    String sketchbookName = favoriteSketchbookService.addFavoriteSketchbook(loginId, favoriteRequestDto.getSketchbookId());
//                    return getResponseEntity(SuccessCode.OK, sketchbookName);
//                } else if (favoriteRequestDto.getAction().equals("delete")) {
//                    favoriteSketchbookService.deleteFavotieSketchbook(loginId, favoriteRequestDto.getSketchbookId());
//                    return getResponseEntity(SuccessCode.OK);
//                } else {
//                    throw new CustomException(ErrorCode.INVALID_ACCESS);
//
//                }
//            } catch (CustomException e) {
//                throw new CustomException(ErrorCode.INVALID_ACCESS);
//            }
//        } else{
//            throw new CustomException(ErrorCode.INVALID_ACCESS);
//        }
//    }

    @PostMapping
    public ResponseEntity<?> toggleFavoriteSketchbook(Authentication authentication, @RequestParam Long sketchbookId) {
        if (authentication.getPrincipal() instanceof Integer) {
            Integer loginId = (Integer) authentication.getPrincipal();
            try {
                String message = favoriteSketchbookService.toggleFavoriteSketchbook(loginId, sketchbookId);
                return ResponseEntity.ok(message);
            } catch (CustomException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }else {
            throw new CustomException(ErrorCode.INVALID_ACCESS);
        }
    }
}
