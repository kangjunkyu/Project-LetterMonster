package com.lemon.backend.domain.friend.service.impl;

import com.lemon.backend.domain.friend.dto.response.GroupResponseDto;
import com.lemon.backend.domain.friend.entity.Friends;
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

        Groups newGroup = Groups.builder()
                .groupName(groupName)
                .owner(user)
                .build();

        return groupRepository.save(newGroup).getId();
    }

    @Override
    public void deleteGroup(Long groupId) {
        Optional<Groups> groupOptional = groupRepository.findById(groupId);
        if (groupOptional.isPresent()) {
            Groups group = groupOptional.get();
            List<Friends> friends = group.getFriendList(); // 혹시라도 속성 이름이 다르다면 적절히 변경해주세요.
            // 기본 그룹 ID를 설정해주세요.
            Long defaultGroupId = 1L;

            Groups defaultGroup = groupRepository.findById(defaultGroupId).get();
            for (Friends friend : friends) {
                System.out.println(friend);
                friend.setGroups(defaultGroup); // 속한 그룹을 기본 그룹으로 설정
                friendsRepository.save(friend);
            }
            groupRepository.deleteById(groupId);
        } else {
            // 그룹이 존재하지 않는 경우 예외 처리
            throw new CustomException(ErrorCode.INVALID_AUTH_CODE);
        }
    }


    @Override
    public Long changeGroupName(Long groupId, String newName){
        Groups group = groupRepository.findById(groupId).get();

        group.setGroupName(newName);

        return groupRepository.save(group).getId();
    }
}
