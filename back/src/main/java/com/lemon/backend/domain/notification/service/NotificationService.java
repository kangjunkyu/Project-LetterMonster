package com.lemon.backend.domain.notification.service;

import com.lemon.backend.domain.notification.dto.NotificationGetDto;
import com.lemon.backend.domain.notification.dto.NotificationSendDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface NotificationService {
    Optional<List<NotificationGetDto>> getAllNotifications(Integer userId);

    Optional<List<NotificationGetDto>> getNotCheckNotifications(Integer userId);

    @Transactional
    boolean sendNotification(String token, String title, String body);

    void checkAllNotification(Integer userId);
}
