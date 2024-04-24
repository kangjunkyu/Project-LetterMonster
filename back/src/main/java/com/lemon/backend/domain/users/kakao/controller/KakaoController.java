package com.lemon.backend.domain.users.kakao.controller;

import com.lemon.backend.domain.users.kakao.service.KakaoAuthService;
import com.lemon.backend.global.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.lemon.backend.global.response.CommonResponseEntity.getResponseEntity;

@Tag(name = "Kakao 컨트롤러", description = "Kakao Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/kakao")
public class KakaoController {

    private final KakaoAuthService kakaoAuthService;

    @Operation(summary = "카카오 소셜 로그인", description = "인가 코드 필요")
    @PostMapping
    public ResponseEntity<?> kakaoLogin(@RequestParam(value = "code") String code){
        return getResponseEntity(SuccessCode.OK, kakaoAuthService.login(code));
    }
}
