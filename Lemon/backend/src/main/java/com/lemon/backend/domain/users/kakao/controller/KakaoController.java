package com.lemon.backend.domain.users.kakao.controller;

import com.lemon.backend.domain.users.service.UserService;
import com.lemon.backend.global.format.code.ApiResponse;
import com.lemon.backend.domain.users.kakao.service.KakaoAuthService;
import com.lemon.backend.domain.users.kakao.dto.KakaoProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kakao")
public class KakaoController {

    private final UserService userService;
    private final KakaoAuthService kakaoAuthService;

}
