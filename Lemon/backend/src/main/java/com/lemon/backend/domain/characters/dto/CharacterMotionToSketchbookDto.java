package com.lemon.backend.domain.characters.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CharacterMotionToSketchbookDto {
    private Long id;
//    private Long motionId;
    private String imageUrl;
}
