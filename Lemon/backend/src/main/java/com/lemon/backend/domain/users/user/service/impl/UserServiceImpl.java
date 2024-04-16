package com.lemon.backend.domain.users.user.service.impl;

import com.lemon.backend.domain.users.user.entity.Adjective;
import com.lemon.backend.domain.users.user.entity.Noun;
import com.lemon.backend.domain.users.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private static final Random random = new Random();
    @Override
    public String makeNickname() {
        Adjective randomAdjective = Adjective.values()[random.nextInt(Adjective.values().length)];
        Noun randomNoun = Noun.values()[random.nextInt(Noun.values().length)];

        return randomAdjective.toString() + " " + randomNoun.toString();
    }
}
