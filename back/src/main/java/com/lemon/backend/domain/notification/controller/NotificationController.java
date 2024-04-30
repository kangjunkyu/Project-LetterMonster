package com.lemon.backend.domain.notification.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Notification 컨트롤러", description = "Notification Controller API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/notification")
public class NotificationController {
}
