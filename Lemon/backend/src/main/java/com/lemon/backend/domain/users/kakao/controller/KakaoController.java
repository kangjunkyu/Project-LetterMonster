package com.lemon.backend.domain.users.kakao.controller;

import com.lemon.backend.domain.users.kakao.service.KakaoAuthService;
import com.lemon.backend.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.lemon.backend.global.response.CommonResponseEntity.getResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kakao")
public class KakaoController {

    private final KakaoAuthService kakaoAuthService;

    @PostMapping
    public ResponseEntity<?> kakaoLogin(@RequestParam(value = "code") String code){
        return getResponseEntity(SuccessCode.OK, kakaoAuthService.login(code));
    }
}
