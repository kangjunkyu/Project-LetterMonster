package com.lemon.backend.domain.friend.service;

import com.lemon.backend.domain.friend.dto.response.GroupResponseDto;

import java.util.List;

public interface GroupsService {

    List<GroupResponseDto> getGroupForUser(Integer userId);

    Long createGroup(Integer userId, String groupName);

    void deleteGroup(Integer userId, Long groupId);

    Long changeGroupName(Long groupId, String newName);
}
