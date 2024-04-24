package com.lemon.backend.domain.sketchbook.dto.requestDto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SketchbookUpdateDto {

//    private Long id;
    private String shareLink;
    private String name;

}
