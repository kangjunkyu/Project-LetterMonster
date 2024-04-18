package com.lemon.backend.domain.letter.dto.responseDto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LetterCreateDto {

//    private Integer sender;
//    private Integer receiver;
    private String content;
    private Long characterMotionId;
    private Long sketchbookId;
//    private Long sketchbookcharactermotionId;
}
