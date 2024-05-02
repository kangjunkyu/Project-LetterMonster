package com.lemon.backend.domain.notification.dto;

import com.lemon.backend.domain.users.user.entity.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationGetDto {

    private Long id;
    private int type;
    private String sketchbookName;
    private String friendName;
    private Boolean isCheack;

    public NotificationGetDto(Long id, int type, String friendName) {
        this.id = id;
        this.type = type;
        this.friendName = friendName;
    }

    private int receiver_id;

}
