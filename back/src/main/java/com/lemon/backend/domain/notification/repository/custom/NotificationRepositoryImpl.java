package com.lemon.backend.domain.notification.repository.custom;

import com.lemon.backend.domain.notification.repository.NotificationRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {

    private final JPAQueryFactory query;

}
