package com.lemon.backend.domain.users.user.dto.response;

import com.querydsl.core.types.dsl.StringPath;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserGetDto {
    private Integer id;
    private Boolean isLanguageSet;
    private String nickname;
    private String nicknameTag;

    public UserGetDto(String nickname, String nicknameTag) {
        this.nickname = nickname;
        this.nicknameTag = nicknameTag;
    }

    public UserGetDto(Integer id, String nickname, String nicknameTag) {
        this.id = id;
        this.nickname = nickname;
        this.nicknameTag = nicknameTag;
    }


    public UserGetDto(Boolean isLanguageSet, String nickname, String nicknameTag) {
        this.isLanguageSet = isLanguageSet;
        this.nickname = nickname;
        this.nicknameTag = nicknameTag;
    }


}
