package com.lemon.backend.domain.users.kakao.controller;

import com.lemon.backend.domain.users.user.service.UserService;
import com.lemon.backend.domain.users.kakao.service.KakaoAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kakao")
public class KakaoController {

    private final UserService userService;
    private final KakaoAuthService kakaoAuthService;

}
