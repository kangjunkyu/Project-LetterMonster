package com.lemon.backend.domain.friend.service;

public interface FriendsService {
    Integer addFriend(Integer userId, Integer friendId);

    void deleteFriend(Integer userId, Integer friendId);

    void changeFriendGroup(Long friendId, Long newGroupId);
}
