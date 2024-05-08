package com.lemon.backend.domain.sketchbook.dto.responseDto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SketchbookSearchGetDto {

    private Long id;
    private String uuid;
    private String name;
    private String tag;
    private String userNickName;

}
