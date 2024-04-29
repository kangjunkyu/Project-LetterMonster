package com.lemon.backend.domain.notification.repository;

import com.lemon.backend.domain.notification.entity.Notification;
import com.lemon.backend.domain.notification.repository.custom.NotificationRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> , NotificationRepositoryCustom {
}
