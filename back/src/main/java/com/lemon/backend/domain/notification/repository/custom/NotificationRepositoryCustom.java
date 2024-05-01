package com.lemon.backend.domain.notification.repository.custom;

import com.lemon.backend.domain.notification.dto.NotificationGetDto;
import com.lemon.backend.domain.notification.entity.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationRepositoryCustom {
    Optional<List<NotificationGetDto>> getNotification(Integer userId);

    Optional<List<NotificationGetDto>> getAllNotification(Integer userId);

    Optional<List<Notification>> findByAll(Integer userId);
}
