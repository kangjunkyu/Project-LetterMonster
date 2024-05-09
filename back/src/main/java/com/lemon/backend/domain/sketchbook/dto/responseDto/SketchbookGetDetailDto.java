package com.lemon.backend.domain.sketchbook.dto.responseDto;

import com.lemon.backend.domain.characters.dto.CharacterToSketchbookDto;
import com.lemon.backend.domain.letter.dto.requestDto.LetterGetDto;
import com.lemon.backend.domain.letter.dto.requestDto.LetterToSketchbookDto;
import com.lemon.backend.domain.users.user.dto.response.UserGetDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SketchbookGetDetailDto {


    private Long id;
    private Boolean isPublic;
    private String shareLink;
    private String name;
    private UserGetDto holder;
    private String uuid;
    private String tag;
    private Boolean isWritePossible;
    private List<SketchbookCharacterMotionGetListDto> sketchbookCharacterMotionList;

    public SketchbookGetDetailDto(Long id, Boolean isPublic, String shareLink, String name, UserGetDto holder, String uuid, String tag) {
        this.id = id;
        this.isPublic = isPublic;
        this.shareLink = shareLink;
        this.name = name;
        this.holder = holder;
        this.uuid = uuid;
        this.tag = tag;
    }
    
    public SketchbookGetDetailDto(Long id, Boolean isPublic, String shareLink, String name, UserGetDto holder, String uuid, String tag, List<SketchbookCharacterMotionGetListDto> sketchbookCharacterMotionList) {
        this.id = id;
        this.isPublic = isPublic;
        this.shareLink = shareLink;
        this.name = name;
        this.holder = holder;
        this.uuid = uuid;
        this.tag = tag;
        this.sketchbookCharacterMotionList = sketchbookCharacterMotionList;
    }

    public SketchbookGetDetailDto(Long id, Boolean isPublic, String shareLink, String name, UserGetDto holder, String uuid, String tag, Boolean isWritePossible) {
        this.id = id;
        this.isPublic = isPublic;
        this.shareLink = shareLink;
        this.name = name;
        this.holder = holder;
        this.uuid = uuid;
        this.tag = tag;
        this.isWritePossible = isWritePossible;
    }


    public SketchbookGetDetailDto(Long id, Boolean isPublic, String shareLink, String name) {
        this.id = id;
        this.isPublic = isPublic;
        this.shareLink = shareLink;
        this.name = name;
    }

    public SketchbookGetDetailDto(Long id, Boolean isPublic, String shareLink, String name, UserGetDto holder) {
        this.id = id;
        this.isPublic = isPublic;
        this.shareLink = shareLink;
        this.name = name;
        this.holder = holder;
    }

    @Override
    public String toString() {
        return "SketchbookGetDetailDto{" +
                "id=" + id +
                ", isPublic=" + isPublic +
                ", shareLink='" + shareLink + '\'' +
                ", name='" + name + '\'' +
                ", holder=" + holder +
                ", uuid='" + uuid + '\'' +
                ", tag='" + tag + '\'' +
                ", isWritePossible=" + isWritePossible +
                '}';
    }
}
