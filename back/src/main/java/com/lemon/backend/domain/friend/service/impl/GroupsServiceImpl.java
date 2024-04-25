package com.lemon.backend.domain.friend.service.impl;

import com.lemon.backend.domain.friend.entity.Groups;
import com.lemon.backend.domain.friend.repository.FriendsRepository;
import com.lemon.backend.domain.friend.repository.GroupsRepository;
import com.lemon.backend.domain.friend.service.GroupsService;
import com.lemon.backend.domain.users.user.entity.Users;
import com.lemon.backend.domain.users.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public void changeGroupName(Long groupId, String newName){
        Groups group = groupRepository.findById(groupId).get();

        group.setGroupName(newName);

        groupRepository.save(group);
    }
}
