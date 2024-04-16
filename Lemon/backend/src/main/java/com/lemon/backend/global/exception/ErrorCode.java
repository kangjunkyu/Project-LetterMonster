package com.lemon.backend.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //스케치북
    SKETCHBOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "스케치북 정보를 찾을 수 없습니다."),

    //편지
    LETTER_NOT_FOUND(HttpStatus.NOT_FOUND, "편지 정보를 찾을 수 없습니다."),

    //회원
    USERS_NOT_FOUND(HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다."),

    //캐릭터
    CHARACTER_NOT_FOUND(HttpStatus.NOT_FOUND, "캐릭터 정보를 찾을 수 없습니다."),

    //캐릭터모션
    CHARACTERMOTION_NOT_FOUND(HttpStatus.NOT_FOUND, "캐릭터 모션 정보를 찾을 수 없습니다."),

    //모션
    MOTION_NOT_FOUND(HttpStatus.NOT_FOUND, "모션 정보를 찾을 수 없습니다.");


    private final HttpStatus status;
    private final String message;

}
