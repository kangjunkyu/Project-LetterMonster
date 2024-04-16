package com.lemon.backend.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    OK(HttpStatus.OK, "OK"),

    //생성 완료
    CREATED(HttpStatus.CREATED, "CREATED");

    private final HttpStatus status;
    private final String message;

}
