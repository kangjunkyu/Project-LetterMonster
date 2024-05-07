package com.lemon.backend.domain.friend;

import com.lemon.backend.domain.friend.entity.Friends;
import com.lemon.backend.domain.friend.entity.GroupsInfo;
import com.lemon.backend.domain.friend.repository.FriendsRepository;
import com.lemon.backend.domain.friend.repository.GroupsRepository;
import com.lemon.backend.domain.friend.service.impl.FriendsServiceImpl;
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

class FriendServiceTest {

    @Mock
    private FriendsRepository friendsRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private GroupsRepository groupsRepository;

    @InjectMocks
    private FriendsServiceImpl friendsService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addFriend_Success() {
        Users user = Users.builder()
                .id(1)
                .build();
        Users friendUser = Users.builder()
                .id(2)
                .build();
        GroupsInfo defaultGroup = GroupsInfo.builder()
                .id(1L)
                .owner(user)
                .groupName("test")
                .build();

        Friends friends = Friends.builder()
                .id(1L)
                .friend(friendUser)
                .users(user)
                .build();

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.findById(2)).thenReturn(Optional.of(friendUser));
        when(groupsRepository.findFirstByUsersId(1)).thenReturn(Optional.of(defaultGroup));
        when(friendsRepository.save(any(Friends.class))).thenReturn(friends);

        assertDoesNotThrow(() -> friendsService.addFriend(1, 2));
        verify(friendsRepository).save(any(Friends.class));
    }

    @Test
    void deleteFriend_Success() {
        Users user = Users.builder()
                .id(1)
                .build();
        Users friendUser = Users.builder()
                .id(2)
                .build();
        GroupsInfo defaultGroup = GroupsInfo.builder()
                .id(1L)
                .owner(user)
                .groupName("test")
                .build();

        Friends friends = Friends.builder()
                .id(1L)
                .friend(friendUser)
                .users(user)
                .build();
        when(friendsRepository.findByUsers_IdAndFriend_Id(1, 2)).thenReturn(Optional.of(friends));
        doNothing().when(friendsRepository).delete(friends);

        assertDoesNotThrow(() -> friendsService.deleteFriend(1, 2));
        verify(friendsRepository).delete(friends);
    }

    @Test
    void deleteFriend_NotFound() {
        when(friendsRepository.findByUsers_IdAndFriend_Id(1, 2)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> friendsService.deleteFriend(1, 2));
    }
}
