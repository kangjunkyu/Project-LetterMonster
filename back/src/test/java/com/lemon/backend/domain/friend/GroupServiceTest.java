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

import java.util.Optional;

class GroupsServiceTest {

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
//
//    @Test
//    void createGroup_Success() {
//        Users user = new Users();
//        user.setId(1);
//        when(userRepository.findById(1)).thenReturn(Optional.of(user));
//        when(groupRepository.save(any(GroupsInfo.class))).thenReturn(new GroupsInfo());
//
//        assertDoesNotThrow(() -> groupsService.createGroup(1, "New Group"));
//        verify(groupRepository).save(any(GroupsInfo.class));
//    }
//
//    @Test
//    void deleteGroup_Success() {
//        GroupsInfo group = new GroupsInfo();
//        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
//        when(groupRepository.findFirstByUsersId(1)).thenReturn(Optional.of(new GroupsInfo()));
//
//        assertDoesNotThrow(() -> groupsService.deleteGroup(1, 1L));
//        verify(groupRepository).deleteById(1L);
//    }
//
//    @Test
//    void deleteGroup_NotFound() {
//        when(groupRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(CustomException.class, () -> groupsService.deleteGroup(1, 1L));
//    }
}
