package com.lemon.backend.domain.friend.repository.custom;

import com.lemon.backend.domain.friend.dto.response.GroupResponseDto;
import com.lemon.backend.domain.friend.entity.Groups;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.lemon.backend.domain.friend.entity.QGroups.groups;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
public class GroupsRepositoryImpl implements GroupsRepositoryCustom {

    private final JPAQueryFactory query;


    @Override
    public Optional<Groups> findFirstByUsersId(Integer userId){

        Groups group = query
                .select(constructor(Groups.class,
                        groups.id,
                        groups.groupName))
                .from(groups)
                .where(groups.owner.id.eq(userId))
                .fetchFirst();
        return Optional.ofNullable(group);
    }
}
