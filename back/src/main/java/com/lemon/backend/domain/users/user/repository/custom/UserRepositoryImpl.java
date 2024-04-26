package com.lemon.backend.domain.users.user.repository.custom;

import com.lemon.backend.domain.users.user.dto.response.UserSearchGetDto;
import com.lemon.backend.domain.users.user.entity.Users;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.lemon.backend.domain.users.user.entity.QUsers.users;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory query;

    @Override
    public Optional<String> findHighestNicknameTagByNickname(String nickname) {
        String highestTag = query.select(users.nicknameTag)
                .from(users)
                .where(users.nickname.eq(nickname))
                .orderBy(users.nicknameTag.desc())
                .fetchFirst();
        return Optional.ofNullable(highestTag);
    }

    @Override
    public void changeNickname(Users user, String newNickname, String newNicknameTag) {
        query.update(users)
                .set(users.nickname, newNickname)
                .set(users.nicknameTag, newNicknameTag)
                .where(users.eq(user))
                .execute();
    }

    @Override
    public List<UserSearchGetDto> findUsersByNickName(String searchNickname){
        return query.select(Projections.constructor(UserSearchGetDto.class,
                users.id,
                users.nickname,
                users.nicknameTag))
                .from(users)
                .where(users.nickname.contains(searchNickname))
                .fetch();
    }

}
