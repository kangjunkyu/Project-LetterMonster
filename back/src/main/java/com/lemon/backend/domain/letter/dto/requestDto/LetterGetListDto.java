package com.lemon.backend.domain.letter.dto.requestDto;

import com.lemon.backend.domain.users.user.dto.response.UserGetDto;
import com.lemon.backend.domain.users.user.entity.Users;
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
    private UserGetDto sender;
    private UserGetDto receiver;
    private String content;
    private LocalDateTime write_time;

    public LetterGetListDto(UserGetDto receiver) {
        this.receiver = receiver;
    }
}
