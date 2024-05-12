package com.lemon.backend.domain.FavoriteSketchbook.repository.custom;

import com.lemon.backend.domain.FavoriteSketchbook.dto.FavoriteSketchbookGetDto;
import com.lemon.backend.domain.FavoriteSketchbook.entity.FavoriteSketchbook;
import com.lemon.backend.domain.sketchbook.dto.responseDto.SketchbookGetFromFavoriteDto;
import com.lemon.backend.domain.users.user.dto.response.UserGetDto;
import com.lemon.backend.global.exception.CustomException;
import com.lemon.backend.global.exception.ErrorCode;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.lemon.backend.domain.FavoriteSketchbook.entity.QFavoriteSketchbook.favoriteSketchbook;
import static com.lemon.backend.domain.sketchbook.entity.QSketchbook.sketchbook;
import static com.lemon.backend.domain.users.user.entity.QUsers.users;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
public class FavoriteSketchbookRepositoryImpl implements FavoriteSketchbookRepositoryCustom {
    private final JPAQueryFactory query;

    @Override
    public Optional<List<FavoriteSketchbookGetDto>> findByUserId(Integer userId) {
        if (userId == null) {
            throw new CustomException(ErrorCode.INVALID_ACCESS);
        }
        List<Tuple> results = query
                .select(favoriteSketchbook.id,
                        sketchbook.id, sketchbook.name, sketchbook.sketchbookUuid, sketchbook.tag,
                        sketchbook.users.nickname, sketchbook.users.nicknameTag)
                .from(favoriteSketchbook)
                .leftJoin(favoriteSketchbook.sketchbook, sketchbook)
                .leftJoin(sketchbook.users, users)
                .where(favoriteSketchbook.user.id.eq(userId))
                .fetch();

        List<FavoriteSketchbookGetDto> dtos = results.stream().map(tuple -> {
            UserGetDto userDto = new UserGetDto(tuple.get(sketchbook.users.nickname), tuple.get(sketchbook.users.nicknameTag));
            SketchbookGetFromFavoriteDto sketchbookDto = new SketchbookGetFromFavoriteDto(
                    tuple.get(sketchbook.id),
                    tuple.get(sketchbook.name),
                    tuple.get(sketchbook.sketchbookUuid),
                    tuple.get(sketchbook.tag),
                    userDto);

            return new FavoriteSketchbookGetDto(
//                    tuple.get(favoriteSketchbook.user),
                    sketchbookDto);

        }).collect(Collectors.toList());

        return Optional.ofNullable(dtos.isEmpty() ? null : dtos);
    }

    @Override
    public List<SketchbookGetFromFavoriteDto> findByUserId2(Integer userId) {
        if (userId == null) {
            throw new CustomException(ErrorCode.INVALID_ACCESS);
        }
        List<Tuple> results = query
                .select(sketchbook.id, sketchbook.name, sketchbook.sketchbookUuid, sketchbook.tag,
                        sketchbook.users.nickname, sketchbook.users.nicknameTag)
                .from(favoriteSketchbook)
                .leftJoin(favoriteSketchbook.sketchbook, sketchbook)
                .leftJoin(sketchbook.users, users)
                .where(favoriteSketchbook.user.id.eq(userId))
                .fetch();

        return results.stream().map(tuple -> {
            UserGetDto userDto = new UserGetDto(tuple.get(sketchbook.users.nickname), tuple.get(sketchbook.users.nicknameTag));
            return new SketchbookGetFromFavoriteDto(
                    tuple.get(sketchbook.id),
                    tuple.get(sketchbook.name),
                    tuple.get(sketchbook.sketchbookUuid),
                    tuple.get(sketchbook.tag),
                    userDto);
        }).collect(Collectors.toList());
    }

    @Override
    public Boolean checkFavoriteSketchbook(Integer userId, Long sketchbookId){
        FavoriteSketchbook found = query
                .select(favoriteSketchbook)
                .from(favoriteSketchbook)
                .where(favoriteSketchbook.user.id.eq(userId).and(favoriteSketchbook.sketchbook.id.eq(sketchbookId)))
                .fetchFirst();
        return found != null;
    }

}
//List<LetterGetListDto> letterDtos = query
//        .select(constructor(LetterGetListDto.class,
//                letter.id,
//                Projections.fields(UserGetDto.class,
//                        letter.sender.nickname,
//                        letter.sender.nicknameTag),
//                Projections.fields(UserGetDto.class,
//                        letter.receiver.nickname,
//                        letter.receiver.nicknameTag),
//                letter.content,
//                letter.isPublic,
//                letter.createdAt)).from(letter)
//        .leftJoin(letter.sender)
//        .leftJoin(letter.receiver)
//        .where(letter.sketchbookCharacterMotion.sketchbook.id.eq(sketchbookId)).fetch();
//        return Optional.ofNullable(letterDtos.isEmpty() ? null : letterDtos);