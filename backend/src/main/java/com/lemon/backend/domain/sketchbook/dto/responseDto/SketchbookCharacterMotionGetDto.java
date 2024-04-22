package com.lemon.backend.domain.sketchbook.dto.responseDto;

import com.lemon.backend.domain.characters.dto.CharacterMotionToSketchbookDto;
import com.lemon.backend.domain.characters.entity.CharacterMotion;
import com.lemon.backend.domain.letter.dto.requestDto.LetterToSketchbookDto;
import com.lemon.backend.domain.sketchbook.entity.Sketchbook;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SketchbookCharacterMotionGetDto {

    private Long id;
    private Sketchbook sketchbook;
    private CharacterMotion characterMotion;
}
