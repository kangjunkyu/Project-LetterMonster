package com.lemon.backend.domain.friend.repository.custom;

import com.lemon.backend.domain.friend.entity.Friends;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class FriendsRepositoryImpl implements FriendsRepositoryCustom {

    private final JPAQueryFactory query;


}
