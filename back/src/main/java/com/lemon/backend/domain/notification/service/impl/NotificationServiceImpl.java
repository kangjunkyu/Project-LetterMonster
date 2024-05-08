package com.lemon.backend.domain.notification.service.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.lemon.backend.domain.notification.dto.NotificationGetDto;
import com.lemon.backend.domain.notification.repository.NotificationRepository;
import com.lemon.backend.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final FirebaseMessaging firebaseMessaging;

    @Override
    public Optional<List<NotificationGetDto>> getAllNotifications(Integer userId) {
        return notificationRepository.getAllNotification(userId); // Optional을 직접 반환
    }

    @Override
    public Optional<List<NotificationGetDto>> getNotCheckNotifications(Integer userId) {
        return notificationRepository.getNotification(userId); // Optional을 직접 반환
    }


    @Transactional
    @Override
    public boolean sendNotification(String token, String title, String body) {
        if (token == null || token.isEmpty() || title == null || title.isEmpty() || body == null || body.isEmpty()) {
            return false;
        }

        String url = "file:///C:/Users/SSAFY/Desktop/LastProject/S10P31B103/back/src/main/resources/img/notification_logo.png";

        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .setImage(url)
                .build();

        Message message = Message.builder()
                .setToken(token)
                .setNotification(notification)
                .build();

        try {
            firebaseMessaging.send(message);
            return true;
        } catch (FirebaseMessagingException e) {
            System.out.println("Failed to send notification: " + e.getMessage());
            return false;
        }
    }

    @Transactional
    @Override
    public void checkAllNotification(Integer userId) {
        List<com.lemon.backend.domain.notification.entity.Notification> allNotification = notificationRepository.findByAll(userId).get();
        if (!allNotification.isEmpty()) {
            for (com.lemon.backend.domain.notification.entity.Notification notification : allNotification) {
                notification.markAsChecked();

                notificationRepository.save(notification);
            }
        }
    }
}
