package com.lemon.backend.domain.friend.service;

public interface GroupsService {

    Long createGroup(Integer userId, String groupName);

    void deleteGroup(Long groupId);

    void changeGroupName(Long groupId, String newName);
}
