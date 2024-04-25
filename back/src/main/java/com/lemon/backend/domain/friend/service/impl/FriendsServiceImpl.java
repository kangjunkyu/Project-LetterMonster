package com.lemon.backend.domain.friend.service.impl;

import com.lemon.backend.domain.friend.entity.Friends;
import com.lemon.backend.domain.friend.entity.Groups;
import com.lemon.backend.domain.friend.repository.FriendsRepository;
import com.lemon.backend.domain.friend.repository.GroupsRepository;
import com.lemon.backend.domain.friend.service.FriendsService;
import com.lemon.backend.domain.users.user.entity.Users;
import com.lemon.backend.domain.users.user.repository.UserRepository;
import com.lemon.backend.global.exception.CustomException;
import com.lemon.backend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class FriendsServiceImpl implements FriendsService {

    private final FriendsRepository friendsRepository;
    private final UserRepository userRepository;
    private final GroupsRepository groupsRepository;

    @Override
    public Integer addFriend(Integer userId, Integer friendId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        Users friendUser = userRepository.findById(friendId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Groups defaultGroup = groupsRepository.findFirstByUsersId(userId).orElseGet(() -> {
            Groups newGroup = Groups.builder()
                    .groupName("기본")
                    .owner(user)
                    .build();
            return groupsRepository.save(newGroup);
        });

        Friends friend = Friends.builder()
                .users(user)
                .friend(friendUser)
                .groups(defaultGroup)
                .build();

        user.getFriendList().add(friend);
        friendUser.getFriendList().add(friend);

        return friendsRepository.save(friend).getId();
    }

    @Override
    public void deleteFriend(Integer userId, Integer friendId) {
        Friends friend = friendsRepository.findByUser_IdAndFriend_Id(userId, friendId).orElseThrow(() -> new CustomException(ErrorCode.INVALID_AUTH_CODE));

        friendsRepository.delete(friend);
    }

    @Override
    public void changeFriendGroup(Long friendId, Long newGroupId) {
        Friends friend = friendsRepository.findById(friendId)
                .orElseThrow(() -> new RuntimeException("Friend relationship not found"));

        Groups newGroup = groupsRepository.findById(newGroupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        friend.setGroups(newGroup);
        friendsRepository.save(friend);
    }

}
