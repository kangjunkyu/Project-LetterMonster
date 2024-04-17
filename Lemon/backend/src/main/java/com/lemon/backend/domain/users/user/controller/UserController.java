package com.lemon.backend.domain.users.user.controller;

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

    @Operation(summary = "액세스 토큰 재발급", description = "Header에 리프레시토큰 필요")
    @PostMapping("/token")
    public ResponseEntity<?> recreateToken(@RequestHeader(value = "Authorization", required = false) String bearerToken){
        return getResponseEntity(SuccessCode.OK, userService.recreateToken(bearerToken));
    }
}
