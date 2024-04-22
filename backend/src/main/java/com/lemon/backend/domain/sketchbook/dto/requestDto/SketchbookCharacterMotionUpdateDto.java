package com.lemon.backend.domain.sketchbook.dto.requestDto;

import com.lemon.backend.domain.characters.dto.CharacterMotionToSketchbookDto;
import com.lemon.backend.domain.letter.dto.requestDto.LetterToSketchbookDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SketchbookCharacterMotionUpdateDto {

    private Long id;
    private Long characterMotionId;
    private Long letterId;

}
