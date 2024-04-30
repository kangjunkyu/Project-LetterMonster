package com.lemon.backend.domain.notification.dto;

import com.lemon.backend.domain.users.user.entity.Users;
import jakarta.persistence.*;

public class NotificationGetDto {

    private Long id;
    private int type;
    private int receiver_id;
    private String sketchbookName;
    private String friendName;

}
