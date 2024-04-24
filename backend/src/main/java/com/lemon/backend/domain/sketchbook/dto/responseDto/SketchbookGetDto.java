package com.lemon.backend.domain.sketchbook.dto.responseDto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SketchbookGetDto {

    private Long id;
    private Boolean isPublic;
    private String shareLink;
    private String name;
//    private Integer userId;

}
