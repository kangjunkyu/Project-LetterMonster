package com.lemon.backend.domain.sketchbook.repository.custom;

import com.lemon.backend.domain.characters.dto.CharacterMotionToSketchbookDto;
import com.lemon.backend.domain.letter.dto.requestDto.LetterToSketchbookDto;
import com.lemon.backend.domain.sketchbook.dto.responseDto.*;
import com.lemon.backend.domain.sketchbook.entity.SketchbookCharacterMotion;
import com.lemon.backend.domain.sketchbook.repository.custom.SketchbookRepositoryCustom;
import com.lemon.backend.domain.users.user.dto.response.UserGetDto;
import com.lemon.backend.global.exception.CustomException;
import com.lemon.backend.global.exception.ErrorCode;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.lemon.backend.domain.characters.entity.QCharacterMotion.characterMotion;
import static com.lemon.backend.domain.characters.entity.QCharacters.characters;
import static com.lemon.backend.domain.letter.entity.QLetter.letter;
import static com.lemon.backend.domain.sketchbook.entity.QSketchbook.sketchbook;
import static com.lemon.backend.domain.sketchbook.entity.QSketchbookCharacterMotion.sketchbookCharacterMotion;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
public class SketchbookRepositoryImpl implements SketchbookRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Optional<List<SketchbookGetSimpleDto>> getSketchList(Integer userId) {
        if (userId == null) {
            throw new CustomException(ErrorCode.INVALID_ACCESS);
        }
        List<SketchbookGetSimpleDto> sketchDtos = query
                .select(constructor(SketchbookGetSimpleDto.class,
                        sketchbook.id,
                        sketchbook.isPublic,
                        sketchbook.shareLink,
                        sketchbook.name,
                        Projections.fields(UserGetDto.class,
                                sketchbook.users.nickname,
                                sketchbook.users.nicknameTag),
                        sketchbook.sketchbookUuid,
                        sketchbook.tag,
                        sketchbook.isWritePossible
                )).from(sketchbook)
                .where(sketchbook.users.id.eq(userId))
                .fetch();
        return Optional.ofNullable(sketchDtos.isEmpty() ? null : sketchDtos);
    }

    @Override
    public Optional<SketchbookGetDto> getSketchSelect(String sketchId) {
        if (sketchId == null) {
            throw new CustomException(ErrorCode.INVALID_ACCESS);
        }
        SketchbookGetDto sketchDto = query
                .select(constructor(SketchbookGetDto.class,
                        sketchbook.id,
                        sketchbook.isPublic,
                        sketchbook.shareLink,
                        sketchbook.name,
                        sketchbook.isWritePossible,
                        Projections.fields(UserGetDto.class,
                                sketchbook.users.nickname,
                                sketchbook.users.nicknameTag),
                        sketchbook.sketchbookUuid,
                        sketchbook.tag
                )).from(sketchbook)
                .where(sketchbook.sketchbookUuid.eq(sketchId))
                .fetchOne();
        return Optional.ofNullable(sketchDto);
    }

    @Override
    public Optional<SketchbookCharacterMotion> findByCharacterMotionAndSketchbook(Long sketchbookId, Long characterMotionId) {
        if (sketchbookId == null || characterMotionId == null) {
            throw new CustomException(ErrorCode.INVALID_ACCESS);
        }
        SketchbookCharacterMotion SketchbookCharacterMotion = query
                .select(constructor(SketchbookCharacterMotion.class,
                        sketchbookCharacterMotion.id,
                        sketchbookCharacterMotion.sketchbook,
                        sketchbookCharacterMotion.characterMotion))
                .from(sketchbookCharacterMotion)
                .where(sketchbookCharacterMotion.sketchbook.id.eq(sketchbookId).and(sketchbookCharacterMotion.characterMotion.id.eq(characterMotionId)))
                .fetchOne();
        return Optional.ofNullable(SketchbookCharacterMotion);
    }

//    @Override
//    public Optional<SketchbookGetDetailDto> getSketchSelect2(Long sketchId){
//        SketchbookGetDetailDto sketchDto = query
//                .select(Projections.constructor(SketchbookGetDetailDto.class,
//                        sketchbook.id,
//                        sketchbook.isPublic,
//                        sketchbook.shareLink,
//                        sketchbook.name
//                )).from(sketchbook)
//                .where(sketchbook.id.eq(sketchId))
//                .fetchOne();
//
//        if(sketchDto != null){
//            List<LetterToSketchbookDto> letterDtos = query
//                    .select(Projections.constructor(LetterToSketchbookDto.class,
//                            letter.id,
//                            letter.sender,
//                            letter.receiver,
//                            letter.content,
//                            letter.createdAt,
//                            letter.characters.id.as("characterId")
//                    )).from(letter)
//                    .where(letter.sketchbook.id.eq(sketchId))
//                    .fetch();
//
//            for (LetterToSketchbookDto letterDto : letterDtos) {
//                // Load character details
//                CharacterToSketchbookDto characterDto = query
//                        .select(Projections.constructor(CharacterToSketchbookDto.class,
//                                characters.id,
//                                characters.nickname,
//                                characters.mainCharacter
//                        )).from(characters)
//                        .where(characters.id.eq(letterDto.getCharactersId()))
//                        .fetchOne();
//
//                // Load character motions
//                if (characterDto != null) {
//                    List<CharacterMotionToSketchbookDto> motions = query
//                            .select(Projections.constructor(CharacterMotionToSketchbookDto.class,
//                                    characterMotion.id,
//                                    characterMotion.url
//                            )).from(characterMotion)
//                            .where(characterMotion.characters.id.eq(characterDto.getId()))
//                            .fetch();
//                    characterDto.setMotions(motions);
//                }
//
//                letterDto.setCharacter(characterDto);
//            }
//
//            sketchDto.setLetterList(letterDtos);
//        }
//        return Optional.ofNullable(sketchDto);
//    }

//    @Override
//    public Optional<SketchbookGetDetailDto> getSketchSelect2(Long sketchId){
//        SketchbookGetDetailDto sketchDto = query
//                .select(Projections.constructor(SketchbookGetDetailDto.class,
//                        sketchbook.id,
//                        sketchbook.isPublic,
//                        sketchbook.shareLink,
//                        sketchbook.name
//                )).from(sketchbook)
//                .where(sketchbook.id.eq(sketchId))
//                .fetchOne();
//
//        if (sketchDto != null) {
//            // Load characters associated with the sketchbook
//            List<CharacterToSketchbookDto> characterDtos = query
//                    .select(Projections.constructor(CharacterToSketchbookDto.class,
//                            characters.id,
//                            characters.nickname,
//                            characters.mainCharacter
//                    )).from(characters)
//                    .leftJoin(letter)
//                    .on(letter.characters.id.eq(characters.id))
//                    .where(letter.sketchbook.id.eq(sketchId))
//                    .fetch();
//
//            for (CharacterToSketchbookDto characterDto : characterDtos) {
//                // Load character motions
//                List<CharacterMotionToSketchbookDto> motions = query
//                        .select(Projections.constructor(CharacterMotionToSketchbookDto.class,
//                                characterMotion.id,
//                                characterMotion.url
//                        )).from(characterMotion)
//                        .where(characterMotion.characters.id.eq(characterDto.getId()))
//                        .fetch();
//                characterDto.setMotionList(motions);
//
//                // Load letters associated with each character
//                List<LetterToSketchbookDto> letters = query
//                        .select(Projections.constructor(LetterToSketchbookDto.class,
//                                letter.id,
//                                letter.sender,
//                                letter.receiver,
//                                letter.content,
//                                letter.createdAt
//                        )).from(letter)
//                        .where(letter.characters.id.eq(characterDto.getId()))
//                        .fetch();
//                characterDto.setLetterList(letters);
//            }
//
//            sketchDto.setCharacterList(characterDtos);
//        }
//
//        return Optional.ofNullable(sketchDto);
//    }

    @Override
    public Optional<SketchbookGetDetailDto> getSketchSelect2(String sketchId) {
        if (sketchId == null) {
            throw new CustomException(ErrorCode.INVALID_ACCESS);
        }

        SketchbookGetDetailDto sketchDto = query
                .select(Projections.constructor(SketchbookGetDetailDto.class,
                        sketchbook.id,
                        sketchbook.isPublic,
                        sketchbook.shareLink,
                        sketchbook.name,
                        Projections.fields(UserGetDto.class,
                                sketchbook.users.nickname,
                                sketchbook.users.nicknameTag),
                        sketchbook.sketchbookUuid,
                        sketchbook.tag,
                        sketchbook.isWritePossible)
                )
                .from(sketchbook)
                .where(sketchbook.sketchbookUuid.eq(sketchId))
                .fetchOne();

        if (sketchDto != null) {
            List<SketchbookCharacterMotionGetListDto> sketchbookCharacterMotions = query
                    .select(Projections.constructor(SketchbookCharacterMotionGetListDto.class,
                            sketchbookCharacterMotion.id,
                            Projections.constructor(CharacterMotionToSketchbookDto.class,
                                    characterMotion.id,
                                    characterMotion.motion.id,
                                    characterMotion.url,
                                    characters.nickname
                            )))
                    .from(sketchbookCharacterMotion)
                    .leftJoin(sketchbookCharacterMotion.characterMotion, characterMotion)
                    .leftJoin(characterMotion.characters, characters)
                    .where(sketchbookCharacterMotion.sketchbook.sketchbookUuid.eq(sketchId))
                    .fetch();

            for (SketchbookCharacterMotionGetListDto motionDto : sketchbookCharacterMotions) {
                List<LetterToSketchbookDto> letters = query
                        .select(constructor(LetterToSketchbookDto.class,
                                letter.id,
//                                letter.sender,
//                                letter.receiver,
                                Projections.fields(UserGetDto.class,
                                        letter.sender.nickname,
                                        letter.sender.nicknameTag),
                                Projections.fields(UserGetDto.class,
                                        letter.receiver.nickname,
                                        letter.receiver.nicknameTag),
                                letter.content,
                                letter.createdAt))
                        .from(letter)
                        .leftJoin(letter.sender)
                        .leftJoin(letter.receiver)
                        .where(letter.sketchbookCharacterMotion.id.eq(motionDto.getId()))
                        .fetch();
                motionDto.setLetterList(letters);

                Long characterMotionId = motionDto.getCharacterMotion() != null ? motionDto.getCharacterMotion().getId() : null;
                if (characterMotionId != null) {
                    CharacterMotionToSketchbookDto characterMotions = query
                            .select(Projections.constructor(CharacterMotionToSketchbookDto.class,
                                    characterMotion.id,
                                    characterMotion.motion.id,
                                    characterMotion.url,
                                    characters.nickname
                            ))
                            .from(characterMotion)
                            .where(characterMotion.id.eq(characterMotionId))
                            .fetchOne();
                    motionDto.setCharacterMotion(characterMotions);
                }
            }
            sketchDto.setSketchbookCharacterMotionList(sketchbookCharacterMotions);
        }

        return Optional.ofNullable(sketchDto);
    }

    @Override
    public SketchbookDetailPageDto getSketchSelect3(String sketchId, Pageable pageable) {
        if (sketchId == null) {
            throw new CustomException(ErrorCode.INVALID_ACCESS);
        }
        SketchbookGetDetailDto sketchDto = query
                .select(Projections.constructor(SketchbookGetDetailDto.class,
                        sketchbook.id,
                        sketchbook.isPublic,
                        sketchbook.shareLink,
                        sketchbook.name,
                        Projections.fields(UserGetDto.class,
                                sketchbook.users.nickname,
                                sketchbook.users.nicknameTag),
                        sketchbook.sketchbookUuid,
                        sketchbook.tag,
                        sketchbook.isWritePossible))
                .from(sketchbook)
                .where(sketchbook.sketchbookUuid.eq(sketchId))
                .fetchOne();
        QueryResults<SketchbookCharacterMotionGetListDto> results = query
                .select(Projections.constructor(SketchbookCharacterMotionGetListDto.class,
                        sketchbookCharacterMotion.id,
                        Projections.constructor(CharacterMotionToSketchbookDto.class,
                                characterMotion.id,
                                characterMotion.motion.id,
                                characterMotion.url,
                                characters.nickname)))
                .from(sketchbookCharacterMotion)
                .leftJoin(sketchbookCharacterMotion.characterMotion, characterMotion)
                .leftJoin(characterMotion.characters, characters)
                .where(sketchbookCharacterMotion.sketchbook.sketchbookUuid.eq(sketchId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<SketchbookCharacterMotionGetListDto> motions = results.getResults();
        long total = results.getTotal();
        motions.forEach(motion -> {
            List<LetterToSketchbookDto> letters = query
                    .select(Projections.constructor(LetterToSketchbookDto.class,
                            letter.id,
                            Projections.fields(UserGetDto.class,
                                    letter.sender.nickname,
                                    letter.sender.nicknameTag),
                            Projections.fields(UserGetDto.class,
                                    letter.receiver.nickname,
                                    letter.receiver.nicknameTag),
                            letter.content,
                            letter.createdAt))
                    .from(letter)
                    .leftJoin(letter.sender)
                    .leftJoin(letter.receiver)
                    .where(letter.sketchbookCharacterMotion.id.eq(motion.getId()))
                    .fetch();

            motion.setLetterList(letters);  // Assuming a setter method is available to set letters
        });

        Page<SketchbookCharacterMotionGetListDto> page = new PageImpl<>(motions, pageable, total);
        return new SketchbookDetailPageDto(sketchDto, page);
    }


    @Override
    public Optional<String> findHighestSketchbookTagByName(String name) {
        String highestSketchbookTag = query.select(sketchbook.tag)
                .from(sketchbook)
                .where(sketchbook.name.eq(name))
                .orderBy(sketchbook.tag.desc())
                .fetchFirst();
        return Optional.ofNullable(highestSketchbookTag);
    }


}

