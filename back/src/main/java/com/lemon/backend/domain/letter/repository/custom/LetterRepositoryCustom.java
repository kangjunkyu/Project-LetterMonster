package com.lemon.backend.domain.letter.repository.custom;

import com.lemon.backend.domain.letter.dto.requestDto.LetterGetListDto;
import com.lemon.backend.domain.letter.dto.requestDto.LetterGetRecentListDto;

import java.util.List;
import java.util.Optional;

public interface LetterRepositoryCustom {
    //    private Long id;
    //    private Integer sender;
    //    private Integer receiver;
    //    private String content;
    //    private Long charactersId;
    //    private Long sketchbookId;
    //    private LocalDate write_time;
    Optional<List<LetterGetListDto>> getLetterList(Long sketchbookId);

    Optional<List<LetterGetRecentListDto>> getLetterThree(Integer userId);
}
