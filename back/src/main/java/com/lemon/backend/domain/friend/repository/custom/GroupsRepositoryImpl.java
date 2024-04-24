package com.lemon.backend.domain.friend.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
public class GroupsRepositoryImpl implements GroupsRepositoryCustom {

    private final JPAQueryFactory query;


}
