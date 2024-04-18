package com.lemon.backend.domain.letter.repository.custom;

import com.lemon.backend.domain.letter.dto.requestDto.LetterGetListDto;
import com.lemon.backend.domain.users.user.dto.response.UserGetDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.lemon.backend.domain.letter.entity.QLetter.letter;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
public class LetterRepositoryImpl implements LetterRepositoryCustom{

    private final JPAQueryFactory query;

//    private Long id;
//    private Integer sender;
//    private Integer receiver;
//    private String content;
//    private Long charactersId;
//    private Long sketchbookId;
//    private LocalDate write_time;
    @Override
    public Optional<List<LetterGetListDto>> getLetterList(Long sketchbookId){
        List<LetterGetListDto> letterDtos = query
                .select(constructor(LetterGetListDto.class,
                        letter.id,
                        Projections.fields(UserGetDto.class,
                                letter.sender.nickname,
                                letter.sender.nicknameTag),
                        Projections.fields(UserGetDto.class,
                                letter.receiver.nickname,
                                letter.receiver.nicknameTag),
                        letter.content,
                        letter.createdAt)).from(letter)
                .leftJoin(letter.sender)
                .leftJoin(letter.receiver)
                .where(letter.sketchbookCharacterMotion.sketchbook.id.eq(sketchbookId)).fetch();
        return Optional.ofNullable(letterDtos.isEmpty() ? null : letterDtos);
    }

}
