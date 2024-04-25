package com.lemon.backend.domain.letter.dto.requestDto;

import com.lemon.backend.domain.users.user.dto.response.UserGetDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LetterToSketchbookDto {
    private Long id;
    private UserGetDto sender;
    private UserGetDto receiver;
    private String content;
    private LocalDateTime write_time;
//    private Long charactersId;
//    private CharacterToSketchbookDto character;

//    public LetterToSketchbookDto(Long id, Integer sender, Integer receiver, String content, LocalDateTime write_time, Long charactersId) {
//        this.id = id;
//        this.sender = sender;
//        this.receiver = receiver;
//        this.content = content;
//        this.write_time = write_time;
//        this.charactersId = charactersId;
//    }

//    public LetterToSketchbookDto(Long id, Integer sender, Integer receiver, String content, LocalDateTime write_time) {
//        this.id = id;
//        this.sender = sender;
//        this.receiver = receiver;
//        this.content = content;
//        this.write_time = write_time;
//    }
//    public LetterToSketchbookDto(Long id, Integer sender, Integer receiver, String content, Long charactersId, LocalDateTime write_time, CharacterToSketchbookDto character) {
//        this.id = id;
//        this.sender = sender;
//        this.receiver = receiver;
//        this.content = content;
//        this.charactersId = charactersId;
//        this.write_time = write_time;
//        this.character = character;  // character 포함
//    }
}
