package com.lemon.backend.domain.friend.repository;

import com.lemon.backend.domain.friend.entity.Friends;
import com.lemon.backend.domain.friend.repository.custom.FriendsRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendsRepository extends JpaRepository<Friends, Long>, FriendsRepositoryCustom {
    Optional<Friends> findByUsers_IdAndFriend_Id(Integer userId, Integer friendId);
}
