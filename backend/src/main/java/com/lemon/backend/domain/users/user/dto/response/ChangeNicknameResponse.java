package com.lemon.backend.domain.users.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChangeNicknameResponse {

    private String nickname;
    private long nicknameTag;
}
