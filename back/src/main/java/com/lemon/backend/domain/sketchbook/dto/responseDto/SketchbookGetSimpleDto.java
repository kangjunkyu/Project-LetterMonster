package com.lemon.backend.domain.sketchbook.dto.responseDto;

import com.lemon.backend.domain.users.user.dto.response.UserGetDto;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SketchbookGetSimpleDto {

    private Long id;
    private Boolean isPublic;
    private String shareLink;
    private String name;
    private UserGetDto holder;
    private String uuid;
    private String tag;
    private Boolean isWritePossible;
    private Boolean isRepresent;
}
