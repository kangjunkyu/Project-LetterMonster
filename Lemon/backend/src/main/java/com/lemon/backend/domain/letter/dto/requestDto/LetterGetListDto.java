package com.lemon.backend.domain.letter.dto.requestDto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LetterGetListDto {
    private Long id;
    private Integer sender;
    private Integer receiver;
    private String content;
    private LocalDateTime write_time;
}
