package com.lemon.backend.domain.friend.dto.request;

import com.lemon.backend.domain.users.user.entity.Users;
import lombok.*;

@Getter
@Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupRequestDto {

    private String name;
    private Users user;
    private Users friend;
}
