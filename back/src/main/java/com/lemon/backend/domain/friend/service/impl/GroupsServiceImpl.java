package com.lemon.backend.domain.friend.service.impl;

import com.lemon.backend.domain.friend.repository.FriendsRepository;
import com.lemon.backend.domain.friend.repository.GroupsRepository;
import com.lemon.backend.domain.friend.service.GroupsService;
import com.lemon.backend.domain.users.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class GroupsServiceImpl implements GroupsService {

    private final GroupsRepository usersFriendsRepository;
    private final FriendsRepository friendsRepository;
    private final UserRepository UserRepository;
    private final GroupsRepository groupRepository;


}
