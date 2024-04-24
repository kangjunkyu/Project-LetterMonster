package com.lemon.backend.domain.sketchbook.repository.custom;

import com.lemon.backend.domain.sketchbook.dto.responseDto.SketchbookGetDto;
import com.lemon.backend.domain.sketchbook.dto.responseDto.SketchbookGetSimpleDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.lemon.backend.domain.sketchbook.entity.QSketchbook.sketchbook;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
public class SketchbookRepositoryImpl implements SketchbookRepositoryCustom{

    private final JPAQueryFactory query;

    @Override
    public Optional<List<SketchbookGetSimpleDto>> getSketchList(Integer userId){
        List<SketchbookGetSimpleDto> sketchDtos = query
                .select(constructor(SketchbookGetSimpleDto.class,
                        sketchbook.shareLink,
                        sketchbook.name
                )).from(sketchbook)
                .where(sketchbook.users.id.eq(userId))
                .fetch();
        return Optional.ofNullable(sketchDtos.isEmpty() ? null : sketchDtos);
    }

    @Override
    public Optional<SketchbookGetDto> getSketchSelect(Long sketchId){
        SketchbookGetDto sketchDto = query
                .select(constructor(SketchbookGetDto.class,
                        sketchbook.id,
                        sketchbook.isPublic,
                        sketchbook.shareLink,
                        sketchbook.name
                )).from(sketchbook)
                .where(sketchbook.id.eq(sketchId))
                .fetchOne();
        return Optional.ofNullable(sketchDto);
    }
}
