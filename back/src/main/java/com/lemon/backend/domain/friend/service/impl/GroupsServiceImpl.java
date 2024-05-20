package com.lemon.backend.domain.friend.service.impl;

import com.lemon.backend.domain.friend.dto.response.GroupResponseDto;
import com.lemon.backend.domain.friend.entity.Friends;
import com.lemon.backend.domain.friend.entity.GroupsInfo;
import com.lemon.backend.domain.friend.repository.FriendsRepository;
import com.lemon.backend.domain.friend.repository.GroupsRepository;
import com.lemon.backend.domain.friend.service.GroupsService;
import com.lemon.backend.domain.users.user.entity.Users;
import com.lemon.backend.domain.users.user.repository.UserRepository;
import com.lemon.backend.global.exception.CustomException;
import com.lemon.backend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class GroupsServiceImpl implements GroupsService {

    private final GroupsRepository usersFriendsRepository;
    private final FriendsRepository friendsRepository;
    private final UserRepository UserRepository;
    private final GroupsRepository groupRepository;
    private final UserRepository userRepository;


    @Override
    public List<GroupResponseDto> getGroupForUser(Integer userId){
        return groupRepository.findAllByUsersId(userId);
    }

    @Override
    public Long createGroup(Integer userId, String groupName){
        Users user = userRepository.findById(userId).get();

        GroupsInfo newGroup = GroupsInfo.builder()
                .groupName(groupName)
                .owner(user)
                .build();

        return groupRepository.save(newGroup).getId();
    }

    @Override
    public void deleteGroup(Integer userId, Long groupId) {
        Optional<GroupsInfo> groupOptional = groupRepository.findById(groupId);
        if (groupOptional.isPresent()) {
            GroupsInfo group = groupOptional.get();
            List<Friends> friends = group.getFriendList();

            GroupsInfo defaultGroup = groupRepository.findFirstByUsersId(userId).get();

            for (Friends friend : friends) {
                System.out.println(friend);
                friend.setGroupsInfo(defaultGroup);
                friendsRepository.save(friend);
            }
            groupRepository.deleteById(groupId);
        } else {
            throw new CustomException(ErrorCode.NOT_FOUND_GROUP);
        }
    }


    @Override
    public Long changeGroupName(Long groupId, String newName){
        GroupsInfo group = groupRepository.findById(groupId).get();

        group.setGroupName(newName);

        return groupRepository.save(group).getId();
    }
}
