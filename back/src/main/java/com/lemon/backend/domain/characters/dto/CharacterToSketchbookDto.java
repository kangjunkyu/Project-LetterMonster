package com.lemon.backend.domain.characters.dto;

import com.lemon.backend.domain.letter.dto.requestDto.LetterToSketchbookDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CharacterToSketchbookDto {
    private Long id;
    private String nickname;
    private Boolean mainCharacter;
    private List<CharacterMotionToSketchbookDto> motionList;
    private List<LetterToSketchbookDto> letterList;

    public CharacterToSketchbookDto(Long id, String nickname, Boolean mainCharacter) {
        this.id = id;
        this.nickname = nickname;
        this.mainCharacter = mainCharacter;
    }

}
