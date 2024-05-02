package com.lemon.backend.domain.notification.controller;

import com.lemon.backend.domain.notification.dto.NotificationGetDto;
import com.lemon.backend.domain.notification.entity.Notification;
import com.lemon.backend.domain.notification.service.NotificationService;
import com.lemon.backend.global.response.CommonResponseEntity;
import com.lemon.backend.global.response.SuccessCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.lemon.backend.global.response.CommonResponseEntity.getResponseEntity;

@Tag(name = "Notification 컨트롤러", description = "Notification Controller API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllnotificationList(Authentication authentication) {
        Integer userId = (Integer) authentication.getPrincipal();
        List<NotificationGetDto> list = notificationService.getAllNotifications(userId).get();
        return getResponseEntity(SuccessCode.OK, list);
    }

    @GetMapping("/uncheck")
    public ResponseEntity<?> getUncheckNotificationList(Authentication authentication) {
        Integer userId = (Integer) authentication.getPrincipal();
        List<NotificationGetDto> list = notificationService.getNotCheckNotifications(userId).get();
        return getResponseEntity(SuccessCode.OK, list);
    }

    @PutMapping()
    public ResponseEntity<?> allCheckNotification(Authentication authentication) {
        Integer userId = (Integer) authentication.getPrincipal();
        notificationService.checkAllNotification(userId);

        return getResponseEntity(SuccessCode.OK);
    }
}

