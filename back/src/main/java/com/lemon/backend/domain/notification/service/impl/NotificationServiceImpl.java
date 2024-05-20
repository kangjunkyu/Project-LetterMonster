package com.lemon.backend.domain.notification.service.impl;

import com.google.firebase.messaging.*;
import com.lemon.backend.domain.notification.dto.NotificationGetDto;
import com.lemon.backend.domain.notification.repository.NotificationRepository;
import com.lemon.backend.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${base.url}")
    private String baseUrl;

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
    public boolean sendNotification(String token, String title, String body, String type) {
        if (token == null || token.isEmpty() || title == null || title.isEmpty() || body == null || body.isEmpty()) {
            return false;
        }

        String imageUrl = "https://letter-monster.s3.ap-northeast-2.amazonaws.com/notify-logo.png";

        String clickUrl = "test";

        if(type.equals("friend")){
            clickUrl = baseUrl;
        }else{
            clickUrl = baseUrl + "/sketchbook/" + type;
        }

        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .setImage(imageUrl)
                .build();

        Message message = Message.builder()
                .setToken(token)
                .setNotification(notification)
                .setWebpushConfig(WebpushConfig.builder()
                        .setNotification(new WebpushNotification(title, body, imageUrl))
                        .setFcmOptions(WebpushFcmOptions.withLink(clickUrl))
                        .build())
                .build();

//        Message message = Message.builder()
//                .setNotification(notification)
//                .setToken(token)
//                .setAndroidConfig(AndroidConfig.builder()
//                        .setNotification(AndroidNotification.builder()
//                                .setIcon("your_icon_resource_name")
//                                .setColor("#ff0000") // 예: 빨간색
//                                .setClickAction("YOUR_INTENT_FILTER")
//                                .build())
//                        .build())
//                .setApnsConfig(ApnsConfig.builder()
//                        .setAps(Aps.builder()
//                                .setCategory("your_notification_category")
//                                .setThreadId("your_specific_thread")
//                                .build())
//                        .build())
//                .setWebpushConfig(WebpushConfig.builder()
//                        .setNotification(new WebpushNotification(title, body, url))
//                        .setFcmOptions(WebpushFcmOptions.withLink("https://lettermon.com"))
//                        .build())
//                .build();


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
