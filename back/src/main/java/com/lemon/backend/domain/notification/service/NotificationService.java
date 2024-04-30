package com.lemon.backend.domain.notification.service;

import com.lemon.backend.domain.notification.dto.NotificationSendDto;
import org.springframework.transaction.annotation.Transactional;

public interface NotificationService {
    @Transactional
    boolean sendNotification(String token, String title, String body);
}
