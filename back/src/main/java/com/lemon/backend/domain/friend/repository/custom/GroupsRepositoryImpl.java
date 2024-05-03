package com.lemon.backend.domain.friend.repository.custom;

import com.lemon.backend.domain.friend.dto.response.FriendResponseDto;
import com.lemon.backend.domain.friend.dto.response.GroupResponseDto;
import com.lemon.backend.domain.friend.entity.GroupsInfo;
import com.lemon.backend.domain.friend.entity.QGroupsInfo;
import com.lemon.backend.global.exception.CustomException;
import com.lemon.backend.global.exception.ErrorCode;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.lemon.backend.domain.friend.entity.QFriends.friends;
import static com.lemon.backend.domain.friend.entity.QGroupsInfo.groupsInfo;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
public class GroupsRepositoryImpl implements GroupsRepositoryCustom {

    private final JPAQueryFactory query;


    @Override
    public Optional<GroupsInfo> findFirstByUsersId(Integer userId) {

        GroupsInfo group = query
                .select(constructor(GroupsInfo.class,
                        groupsInfo.id,
                        groupsInfo.groupName))
                .from(groupsInfo)
                .where(groupsInfo.owner.id.eq(userId))
                .fetchFirst();
        return Optional.ofNullable(group);
    }

    @Override
    public List<GroupResponseDto> findAllByUsersId(Integer userId) {
        if (userId == null) {
            throw new CustomException(ErrorCode.INVALID_ACCESS);
        }
        List<GroupResponseDto> groupDtos = query
                .select(Projections.constructor(GroupResponseDto.class,
                        groupsInfo.id,
                        groupsInfo.groupName))
                .from(groupsInfo)
                .where(groupsInfo.owner.id.eq(userId))
                .fetch();

        for (GroupResponseDto groupDto : groupDtos) {
            List<FriendResponseDto> friendDtos = query
                    .select(Projections.constructor(FriendResponseDto.class,
                            friends.friend.id,
                            friends.friend.nickname,
                            friends.friend.nicknameTag
                    )).from(friends)
                    .where(friends.groupsInfo.id.eq(groupDto.getId()))
                    .fetch();
            groupDto.setFriendList(friendDtos);
        }
        return groupDtos; // 직접 리스트 반환, 비어있는 경우 빈 리스트 반환
    }

}
