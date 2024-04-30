package com.lemon.backend.domain.notification.dto;

import com.lemon.backend.domain.users.user.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationSendDto {

    private String title;
    private String body;

}
