package com.lemon.backend.domain.friend.service.impl;

import com.lemon.backend.domain.friend.dto.response.FriendResponseDto;
import com.lemon.backend.domain.friend.dto.response.GroupResponseDto;
import com.lemon.backend.domain.friend.entity.Friends;
import com.lemon.backend.domain.friend.entity.GroupsInfo;
import com.lemon.backend.domain.friend.repository.FriendsRepository;
import com.lemon.backend.domain.friend.repository.GroupsRepository;
import com.lemon.backend.domain.friend.service.FriendsService;
import com.lemon.backend.domain.notification.entity.Notification;
import com.lemon.backend.domain.notification.repository.NotificationRepository;
import com.lemon.backend.domain.notification.service.NotificationService;
import com.lemon.backend.domain.users.user.dto.response.UserGetDto;
import com.lemon.backend.domain.users.user.entity.Users;
import com.lemon.backend.domain.users.user.repository.UserRepository;
import com.lemon.backend.global.exception.CustomException;
import com.lemon.backend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class FriendsServiceImpl implements FriendsService {

    private final FriendsRepository friendsRepository;
    private final UserRepository userRepository;
    private final GroupsRepository groupsRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;

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

        List<GroupResponseDto> list = groupsRepository.findAllByUsersId(userId);

        for(GroupResponseDto group : list){
                for(FriendResponseDto group2 : group.getFriendList()){
                    if(group2.getId().equals(friendId)){
                throw new CustomException(ErrorCode.CAN_NOT_ADD_FRIEND);
                    }
                }
        }

        Friends friend = Friends.builder()
                .users(user)
                .friend(friendUser)
                .groupsInfo(defaultGroup)
                .build();

        user.getFriendList().add(friend);
        friendUser.getFriendList().add(friend);

        Notification notification = Notification.builder()
                .receiver(user)
                .type(2)
                .friendName(friend.getFriend().getNickname())
                .build();

        String body = null;

        if(friend.getFriend().getNickname() != null){
            body = "[ " + friend.getFriend().getNickname() + " ] 님께서 친구로 추가했어요";
            notificationRepository.save(notification);
        }

        String title = "LEMON";
        if(!notificationService.sendNotification(user.getNotificationToken(), title, body)){
            throw new CustomException(ErrorCode.NOT_FOUND_NOTIFICATION);
        }

        return friendsRepository.save(friend).getFriend().getNickname();
    }

    @Transactional
    @Override
    public void deleteFriend(Integer userId, Integer friendId) {
        Friends friend = friendsRepository.findByUsers_IdAndFriend_Id(userId, friendId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EXCEPTION));

        friendsRepository.delete(friend);
    }

    @Transactional
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
