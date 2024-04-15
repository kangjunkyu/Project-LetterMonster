package com.lemon.backend.global.format.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class ResponseCode {

    private final HttpStatus status;
    private final String message;

}
