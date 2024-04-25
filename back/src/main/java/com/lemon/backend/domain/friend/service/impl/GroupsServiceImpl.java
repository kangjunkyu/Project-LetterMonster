package com.lemon.backend.domain.friend.service.impl;

import com.lemon.backend.domain.friend.dto.response.GroupResponseDto;
import com.lemon.backend.domain.friend.entity.Groups;
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

        Groups newGroup = Groups.builder()
                .groupName(groupName)
                .owner(user)
                .build();

        return groupRepository.save(newGroup).getId();
    }

    @Override
    public void deleteGroup(Long groupId){
        groupRepository.deleteById(groupId);
    }

    @Override
    public Long changeGroupName(Long groupId, String newName){
        Groups group = groupRepository.findById(groupId).get();

        group.setGroupName(newName);

        return groupRepository.save(group).getId();
    }
}
