package com.lemon.backend.domain.notification.service.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.lemon.backend.domain.notification.dto.NotificationSendDto;
import com.lemon.backend.domain.notification.repository.NotificationRepository;
import com.lemon.backend.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final FirebaseMessaging firebaseMessaging;


    @Transactional
    @Override
    public boolean sendNotification(String token, String title, String body) {
        if(token == null || token.isEmpty() || title == null || title.isEmpty() || body == null || body.isEmpty()) {
            return true;
        }
            Notification notification = Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build();

        Message message = Message.builder()
                .setToken(token)
                .setNotification(notification)
                .build();

        try {
            firebaseMessaging.send(message);
            return true;
        } catch (FirebaseMessagingException e) {
            return false;
        }
    }
}
