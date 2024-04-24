package com.lemon.backend.domain.letter.dto.requestDto;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LetterGetDto {
    private Long id;
    private Integer sender;
    private Integer receiver;
    private String content;
    private Long charactersId;
    private Long sketchbookId;
    private LocalDate write_time;
}
