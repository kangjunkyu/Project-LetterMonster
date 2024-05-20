package com.lemon.backend.domain.notification.dto;

import com.lemon.backend.domain.users.user.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationGetDto {

    private Long id;
    private int type;
    private String sketchbookName;
    private String sketchbookTag;
    private String sketchbookUuid;
    private String friendName;
    private String friendTag;
    private Boolean isCheck;
    private LocalDateTime createdAt;

    public NotificationGetDto(Long id, int type, String friendName) {
        this.id = id;
        this.type = type;
        this.friendName = friendName;
    }


}
