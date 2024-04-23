package com.lemon.backend.domain.sketchbook.dto.responseDto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SketchbookGetAllDto {

    private Long id;
    private String name;
    private String tag;

}
