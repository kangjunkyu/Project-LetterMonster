package com.lemon.backend.domain.friend;

import com.lemon.backend.domain.friend.entity.Friends;
import com.lemon.backend.domain.friend.entity.GroupsInfo;
import com.lemon.backend.domain.friend.repository.FriendsRepository;
import com.lemon.backend.domain.friend.repository.GroupsRepository;
import com.lemon.backend.domain.friend.service.impl.GroupsServiceImpl;
import com.lemon.backend.domain.users.user.entity.Users;
import com.lemon.backend.domain.users.user.repository.UserRepository;
import com.lemon.backend.global.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Optional;

class GroupServiceTest {

    @Mock
    private GroupsRepository groupRepository;
    @Mock
    private FriendsRepository friendsRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GroupsServiceImpl groupsService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createGroup_Success() {
        Users user = Users.builder()
                .id(1)
                .build();
        GroupsInfo groupsInfo = GroupsInfo.builder()
                .id(1L)
                .groupName("test")
                .owner(user)
                .build();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(groupRepository.save(any(GroupsInfo.class))).thenReturn(groupsInfo);

        assertDoesNotThrow(() -> groupsService.createGroup(1, "New Group"));
        verify(groupRepository).save(any(GroupsInfo.class));
    }

    @Test
    void deleteGroup_Success() {
        Users user = Users.builder()
                .id(1)
                .build();
        Users friend = Users.builder()
                .id(2)
                .build();

        GroupsInfo groupsInfo = GroupsInfo.builder()
                .id(1L)
                .groupName("test")
                .owner(user)
                .friendList(new ArrayList<>())
                .build();

        Friends friends = Friends.builder()
                .id(1L)
                .friend(friend)
                .users(user)
                .groupsInfo(groupsInfo)
                .build();
        groupsInfo.getFriendList().add(friends);
        when(groupRepository.findById(1L)).thenReturn(Optional.of(groupsInfo));
        when(groupRepository.findFirstByUsersId(1)).thenReturn(Optional.of(groupsInfo));

        assertDoesNotThrow(() -> groupsService.deleteGroup(1, 1L));
        verify(groupRepository).deleteById(1L);
    }

    @Test
    void deleteGroup_NotFound() {
        when(groupRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> groupsService.deleteGroup(1, 1L));
    }
}
