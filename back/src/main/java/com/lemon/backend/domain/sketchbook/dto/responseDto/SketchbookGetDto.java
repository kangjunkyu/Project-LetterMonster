package com.lemon.backend.domain.sketchbook.dto.responseDto;

import com.lemon.backend.domain.letter.dto.requestDto.LetterGetDto;
import com.lemon.backend.domain.letter.dto.requestDto.LetterToSketchbookDto;
import com.lemon.backend.domain.users.user.dto.response.UserGetDto;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SketchbookGetDto {

    private Long id;
    private Boolean isPublic;
    private String shareLink;
    private String name;
    private Boolean isWritePossible;
    private UserGetDto holder;
    private String uuid;
    private String tag;
    private Boolean isRepresent;

    public SketchbookGetDto(Long id, Boolean isPublic, String shareLink, String name, Boolean isWritePossible, UserGetDto holder, String uuid, String tag) {
        this.id = id;
        this.isPublic = isPublic;
        this.shareLink = shareLink;
        this.name = name;
        this.isWritePossible = isWritePossible;
        this.holder = holder;
        this.uuid = uuid;
        this.tag = tag;
    }

    public SketchbookGetDto(Long id, Boolean isPublic, String shareLink, String name, UserGetDto holder, String uuid, String tag, Boolean isWritePossible) {
        this.id = id;
        this.isPublic = isPublic;
        this.shareLink = shareLink;
        this.name = name;
        this.holder = holder;
        this.uuid = uuid;
        this.tag = tag;
        this.isWritePossible = isWritePossible;
    }

    public SketchbookGetDto(Boolean isRepresent) {
        this.isRepresent = isRepresent;
    }

}
