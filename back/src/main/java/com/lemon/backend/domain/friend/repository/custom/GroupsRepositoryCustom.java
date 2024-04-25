package com.lemon.backend.domain.friend.repository.custom;

import com.lemon.backend.domain.friend.dto.response.GroupResponseDto;
import com.lemon.backend.domain.friend.entity.Groups;

import java.util.Optional;

public interface GroupsRepositoryCustom {
    Optional<Groups> findFirstByUsersId(Integer userId);
}
