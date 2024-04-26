package com.lemon.backend.domain.friend.repository.custom;

import com.lemon.backend.domain.friend.dto.response.GroupResponseDto;
import com.lemon.backend.domain.friend.entity.GroupsInfo;

import java.util.List;
import java.util.Optional;

public interface GroupsRepositoryCustom {
    Optional<GroupsInfo> findFirstByUsersId(Integer userId);

    List<GroupResponseDto> findAllByUsersId(Integer userId);
}
