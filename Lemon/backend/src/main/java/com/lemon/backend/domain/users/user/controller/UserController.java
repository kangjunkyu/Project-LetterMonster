package com.lemon.backend.domain.users.user.controller;

import com.lemon.backend.domain.users.user.dto.request.ChangeNicknameRequest;
import com.lemon.backend.domain.users.user.service.UserService;
import com.lemon.backend.global.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.lemon.backend.global.response.CommonResponseEntity.getResponseEntity;

@Tag(name = "User 컨트롤러", description = "User Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "소셜 로그인 url 재공", description = "인가코드를 얻어오는 url을 리턴합니다.")
    @GetMapping("/login/{provider}")
    public ResponseEntity<?> getSocialLoginUrl(@PathVariable String provider){
        return getResponseEntity(SuccessCode.OK, userService.getSocialLoginUrl(provider));
    }

    @Operation(summary = "액세스 토큰 재발급", description = "Header에 리프레시토큰 필요")
    @PostMapping("/token")
    public ResponseEntity<?> recreateToken(@RequestHeader(value = "Authorization", required = false) String bearerToken){
        return getResponseEntity(SuccessCode.OK, userService.recreateToken(bearerToken));
    }

    @Operation(summary = "로그아웃", description = "Header의 액세스 토큰을 이용하여 로그아웃을 합니다.")
    @PostMapping("/logout")
    public ResponseEntity<?> recreateToken(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        userService.logout(userId);
        return getResponseEntity(SuccessCode.OK);
    }

    @Operation(summary = "닉네임 변경", description = "Header의 액세스 토큰을 이용하여 닉네임 변경을 합니다.")
    @PostMapping("/nickname")
    public ResponseEntity<?> changeNickname(HttpServletRequest httpServletRequest, @RequestBody ChangeNicknameRequest request){
        Integer userId = (Integer) httpServletRequest.getAttribute("userId");
        return getResponseEntity(SuccessCode.OK, userService.changeNickname(userId, request));
    }

    @Operation(summary = "회원탈퇴", description = "회원 탈퇴를 진행합니다.")
    @DeleteMapping()
    public ResponseEntity<?> withdrawUser(HttpServletRequest httpServletRequest){
        Integer userId = (Integer) httpServletRequest.getAttribute("userId");
        userService.withdrawUser(userId);
        return getResponseEntity(SuccessCode.ACCEPTED);
    }
}
