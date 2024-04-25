package com.lemon.backend.domain.users.user.repository.custom;

import com.lemon.backend.domain.users.user.entity.Users;

import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<String> findHighestNicknameTagByNickname(String nickname);
    void changeNickname(Users user, String nickname, String valueOf);
}
