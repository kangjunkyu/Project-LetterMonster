package com.lemon.backend.domain.users.user.repository.custom;

import com.lemon.backend.domain.users.user.dto.response.UserSearchAndFriendResponse;
import com.lemon.backend.domain.users.user.dto.response.UserSearchGetDto;
import com.lemon.backend.domain.users.user.entity.Users;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.lemon.backend.domain.friend.entity.QFriends.friends;
import static com.lemon.backend.domain.users.user.entity.QUsers.users;
import static com.querydsl.jpa.JPAExpressions.select;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory query;

    @PersistenceContext
    private EntityManager entityManager;

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

    @Transactional
    @Override
    public void updateFirebaseToken(Integer userId, String firebaseToken){
        query.update(users)
                .set(users.notificationToken, firebaseToken)
                .where(users.id.eq(userId))
                .execute();
    }

    @Override
    public List<UserSearchAndFriendResponse> findUserAndFriend(Integer userId, String nickname) {

        var isFriendSubquery = select(friends.id)
                .from(friends)
                .where(friends.users.id.eq(userId)
                        .and(friends.friend.id.eq(users.id)));


        return query.select(Projections.constructor(UserSearchAndFriendResponse.class,
                        users.id,
                        users.nickname,
                        users.nicknameTag,
                        Expressions.asBoolean(isFriendSubquery.exists()).as("isFriend")))
                .from(users)
                .where(users.nickname.containsIgnoreCase(nickname)
                        .and(users.id.ne(userId)))  // 검색하는 사용자 제외
                .fetch();
    }

}
