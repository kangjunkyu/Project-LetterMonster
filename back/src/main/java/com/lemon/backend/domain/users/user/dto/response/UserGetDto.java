package com.lemon.backend.domain.users.user.dto.response;

import com.querydsl.core.types.dsl.StringPath;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserGetDto {
    private Boolean isLanguageSet;
    private String nickname;
    private String nicknameTag;

}
