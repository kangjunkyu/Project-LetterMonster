package com.lemon.backend.domain.notification.repository.custom;

import com.lemon.backend.domain.notification.dto.NotificationGetDto;
import com.lemon.backend.domain.notification.entity.Notification;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.lemon.backend.domain.notification.entity.QNotification.notification;

@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Optional<List<NotificationGetDto>> getNotification(Integer userId) {
        return Optional.ofNullable(query.select(Projections.constructor(NotificationGetDto.class,
                        notification.id,
                        notification.type,
                        notification.friendName
                ))
                .where(notification.receiver.id.eq(userId)
                        .and(notification.isCheck.eq(false)))
                .fetch());
    }

    @Override
    public Optional<List<NotificationGetDto>> getAllNotification(Integer userId) {
        return Optional.ofNullable(query.select(Projections.constructor(NotificationGetDto.class,
                        notification.id,
                        notification.type,
                        notification.friendName
                        ))
                .where(notification.receiver.id.eq(userId))
                .fetch());
    }

    @Override
    public Optional<List<Notification>> findByAll(Integer userId){
        return Optional.ofNullable(query.select(Projections.constructor(Notification.class,
                notification.id,
                notification.type,
                notification.receiver,
                notification.friendName,
                notification.isCheck)).where(notification.receiver.id.eq(userId)).fetch());
    }
}
