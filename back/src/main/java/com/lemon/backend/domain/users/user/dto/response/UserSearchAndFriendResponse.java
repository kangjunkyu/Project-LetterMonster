package com.lemon.backend.domain.users.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchAndFriendResponse {
    private Integer userId;
    private String nickname;
    private String nicknameTag;
    private boolean isFriend;
}
