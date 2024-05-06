package com.lemon.backend.domain.notification;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.lemon.backend.domain.notification.dto.NotificationGetDto;
import com.lemon.backend.domain.notification.repository.NotificationRepository;
import com.lemon.backend.domain.notification.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private FirebaseMessaging firebaseMessaging;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    public NotificationServiceImplTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllNotifications_Success() {
        // Given
        Integer userId = 1;
        List<NotificationGetDto> notifications = Arrays.asList(
                new NotificationGetDto(1L, 1, "Test Title 1"),
                new NotificationGetDto(2L, 2, "Test Title 2")
        );
        when(notificationRepository.getAllNotification(userId)).thenReturn(Optional.of(notifications));

        // When
        Optional<List<NotificationGetDto>> result = notificationService.getAllNotifications(userId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(notifications.size(), result.get().size());
        assertEquals(notifications, result.get());
    }

    @Test
    void getAllNotifications_NoNotifications() {
        // Given
        Integer userId = 1;
        when(notificationRepository.getAllNotification(userId)).thenReturn(Optional.empty());

        // When
        Optional<List<NotificationGetDto>> result = notificationService.getAllNotifications(userId);

        // Then
        assertFalse(result.isPresent(), "The result should be empty.");
    }

    @Test
    void getNotCheckNotifications_Success() {
        // Given
        Integer userId = 1;
        List<NotificationGetDto> notifications = Arrays.asList(
                new NotificationGetDto(1L, 1, "Test Title 1"),
                new NotificationGetDto(2L, 2, "Test Title 2")
        );
        when(notificationRepository.getNotification(userId)).thenReturn(Optional.of(notifications));

        // When
        Optional<List<NotificationGetDto>> result = notificationService.getNotCheckNotifications(userId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(notifications.size(), result.get().size());
        assertEquals(notifications, result.get());
    }

    @Test
    void getNotCheckNotifications_NoNotifications() {
        // Given
        Integer userId = 1;
        when(notificationRepository.getNotification(userId)).thenReturn(Optional.empty());

        // When
        Optional<List<NotificationGetDto>> result = notificationService.getNotCheckNotifications(userId);

        // Then
        assertFalse(result.isPresent(), "The result should be empty.");
    }

    @Test
    void sendNotification_Success() throws FirebaseMessagingException {
        // Given
        String token = "test-token";
        String title = "Test Title";
        String body = "Test Body";

        // When
        boolean result = notificationService.sendNotification(token, title, body);

        // Then
        assertTrue(result);
        verify(firebaseMessaging).send(any(Message.class));
    }

    @Test
    void sendNotification_EmptyToken() {
        // Given
        String token = "";
        String title = "Test Title";
        String body = "Test Body";

        // When
        boolean result = notificationService.sendNotification(token, title, body);

        // Then
        assertFalse(result);
        verifyNoInteractions(firebaseMessaging);
    }

    @Test
    void checkAllNotification() {
        // Given
        Integer userId = 1;
        List<com.lemon.backend.domain.notification.entity.Notification> notifications = Arrays.asList(
                new com.lemon.backend.domain.notification.entity.Notification(),
                new com.lemon.backend.domain.notification.entity.Notification()
        );
        when(notificationRepository.findByAll(userId)).thenReturn(Optional.of(notifications));

        // When
        notificationService.checkAllNotification(userId);

        // Then
        for (com.lemon.backend.domain.notification.entity.Notification notification : notifications) {
            assertTrue(notification.getIsCheck());
        }
        verify(notificationRepository, times(notifications.size())).save(any());
    }
}
