package com.lemon.backend.domain.characters.repository;

import com.lemon.backend.domain.characters.dto.response.CharacterInfoDto;
import com.lemon.backend.domain.characters.entity.Characters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<Characters, Long> {

    @Query("SELECT DISTINCT ch.nickname AS nickname, ch.url AS imageUrl " +
            "FROM Characters ch " +
            "WHERE ch.id = :characterId")
    Optional<CharacterInfoDto> findAllByUserId(Long characterId);

    @Query("SELECT c FROM Characters c WHERE c.users.id = :userId")
    List<Characters> findByUserId(Long userId);

}
