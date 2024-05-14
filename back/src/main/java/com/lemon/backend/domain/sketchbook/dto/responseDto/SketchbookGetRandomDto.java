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
public class SketchbookGetRandomDto {

    private Long id;
    private String shareLink;
    private String name;
    private String uuid;
    private String tag;
    private UserGetDto holder;

}
