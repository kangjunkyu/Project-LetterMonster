package com.lemon.backend.domain.friend.dto.response;

import com.lemon.backend.domain.users.user.dto.response.UserGetDto;
import com.lemon.backend.domain.users.user.entity.Users;
import lombok.*;

@Getter
@Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendResponseDto {
    private Long id;
    private Users user;
    private Users friend;
}
