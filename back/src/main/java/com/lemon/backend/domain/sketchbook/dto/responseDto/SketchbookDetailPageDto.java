package com.lemon.backend.domain.sketchbook.dto.responseDto;

import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SketchbookDetailPageDto {
    private SketchbookGetDetailDto sketchbookDetail;
    private Page<SketchbookCharacterMotionGetListDto> characterMotions;

    public SketchbookDetailPageDto(SketchbookGetDetailDto sketchbookDetail, Page<SketchbookCharacterMotionGetListDto> characterMotions) {
        this.sketchbookDetail = sketchbookDetail;
        this.characterMotions = characterMotions;
    }

}