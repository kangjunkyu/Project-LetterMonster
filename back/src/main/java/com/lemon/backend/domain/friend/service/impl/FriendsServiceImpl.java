package com.lemon.backend.domain.friend.service.impl;

import com.lemon.backend.domain.friend.entity.Friends;
import com.lemon.backend.domain.friend.entity.GroupsInfo;
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
    public String addFriend(Integer userId, Integer friendId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        Users friendUser = userRepository.findById(friendId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        GroupsInfo defaultGroup = groupsRepository.findFirstByUsersId(userId).orElseGet(() -> {
            GroupsInfo newGroup = GroupsInfo.builder()
                    .groupName("기본")
                    .owner(user)
                    .build();
            return groupsRepository.save(newGroup);
        });

        Friends friend = Friends.builder()
                .users(user)
                .friend(friendUser)
                .groupsInfo(defaultGroup)
                .build();

        user.getFriendList().add(friend);
        friendUser.getFriendList().add(friend);

        return friendsRepository.save(friend).getFriend().getNickname();
    }

    @Override
    public void deleteFriend(Integer userId, Integer friendId) {
        Friends friend = friendsRepository.findByUsers_IdAndFriend_Id(userId, friendId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EXCEPTION));

        friendsRepository.delete(friend);
    }

    @Override
    public void changeFriendGroup(Long friendId, Long newGroupId) {
        Friends friend = friendsRepository.findById(friendId)
                .orElseThrow(() -> new RuntimeException("Friend relationship not found"));

        GroupsInfo newGroup = groupsRepository.findById(newGroupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        friend.setGroupsInfo(newGroup);
        friendsRepository.save(friend);
    }

}
