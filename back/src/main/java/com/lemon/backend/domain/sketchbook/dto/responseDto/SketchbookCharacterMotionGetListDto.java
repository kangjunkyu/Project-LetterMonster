package com.lemon.backend.domain.sketchbook.dto.responseDto;

import com.lemon.backend.domain.characters.dto.CharacterMotionToSketchbookDto;
import com.lemon.backend.domain.letter.dto.requestDto.LetterGetListDto;
import com.lemon.backend.domain.letter.dto.requestDto.LetterToSketchbookDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SketchbookCharacterMotionGetListDto {

    private Long id;
    private CharacterMotionToSketchbookDto characterMotion;
    private List<LetterToSketchbookDto> letterList;
    public SketchbookCharacterMotionGetListDto(Long id) {
        this.id = id;
    }

    public SketchbookCharacterMotionGetListDto(Long id, CharacterMotionToSketchbookDto characterMotion) {
        this.id = id;
        this.characterMotion = characterMotion;
    }
}
