package com.lemon.backend.domain.users.kakao.controller;

import com.lemon.backend.domain.users.kakao.service.KakaoAuthService;
import com.lemon.backend.global.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.lemon.backend.global.response.CommonResponseEntity.getResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kakao")
public class KakaoController {

    private final KakaoAuthService kakaoAuthService;

    @PostMapping
    @Operation(summary = "카카오 로그인", description = "AUTHORIZE_CODE를 이용해 사용자 로그인을 합니다.")
    public ResponseEntity<?> kakaoLogin(@RequestParam(value = "code") String code){
        return getResponseEntity(SuccessCode.OK, kakaoAuthService.login(code));
    }
}
