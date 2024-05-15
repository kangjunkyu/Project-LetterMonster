package com.lemon.backend.domain.FavoriteSketchbook.repository;

import com.lemon.backend.domain.FavoriteSketchbook.entity.FavoriteSketchbook;
import com.lemon.backend.domain.FavoriteSketchbook.repository.custom.FavoriteSketchbookRepositoryCustom;
import com.lemon.backend.domain.sketchbook.entity.Sketchbook;
import com.lemon.backend.domain.users.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteSketchbookRepository extends JpaRepository<FavoriteSketchbook, Long>, FavoriteSketchbookRepositoryCustom {

    boolean existsByUserAndSketchbook(Users user, Sketchbook sketchbook);

    Optional<FavoriteSketchbook> findByUserAndSketchbook(Users user, Sketchbook sketchbook);

//    List<FavoriteSketchbook> findByUserId(Integer userId);
}
