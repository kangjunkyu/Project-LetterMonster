package com.lemon.backend.domain.friend;

import com.lemon.backend.domain.friend.entity.Friends;
import com.lemon.backend.domain.friend.entity.GroupsInfo;
import com.lemon.backend.domain.friend.repository.FriendsRepository;
import com.lemon.backend.domain.friend.repository.GroupsRepository;
import com.lemon.backend.domain.friend.service.impl.FriendsServiceImpl;
import com.lemon.backend.domain.letter.service.impl.LetterServiceImpl;
import com.lemon.backend.domain.notification.entity.Notification;
import com.lemon.backend.domain.notification.repository.NotificationRepository;
import com.lemon.backend.domain.notification.service.NotificationService;
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
    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private NotificationService notificationService;


    @InjectMocks
    private FriendsServiceImpl friendsService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    void addFriend_Success() {
//
//        String notificationToken = "Test";
//
//        Users user = Users.builder()
//                .id(1)
//                .notificationToken(notificationToken)
//                .build();
//        Users friendUser = Users.builder()
//                .id(2)
//                .notificationToken(notificationToken)
//                .build();
//        GroupsInfo defaultGroup = GroupsInfo.builder()
//                .id(1L)
//                .owner(user)
//                .groupName("test")
//                .build();
//
//        Friends friends = Friends.builder()
//                .id(1L)
//                .friend(friendUser)
//                .users(user)
//                .build();
//
//        Notification notification = Notification.builder()
//                .id(1L)
//                .type(2)
//                .receiver(friendUser)
//                .friendName("test")
//                .isCheck(false)
//                .build();
//
//        when(userRepository.findById(1)).thenReturn(Optional.of(user));
//        when(userRepository.findById(2)).thenReturn(Optional.of(friendUser));
//        when(groupsRepository.findFirstByUsersId(1)).thenReturn(Optional.of(defaultGroup));
//        when(friendsRepository.save(any(Friends.class))).thenReturn(friends);
//        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);
//        when(notificationService.sendNotification(anyString(), anyString(), anyString())).thenReturn(true);
//
//        assertDoesNotThrow(() -> friendsService.addFriend(1, 2),"Created letter ID should not be null");
//        verify(notificationService).sendNotification(anyString(), anyString(), anyString());
//        verify(friendsRepository).save(any(Friends.class));
//    }

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
