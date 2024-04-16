package com.lemon.backend.global.exception;

import com.lemon.backend.global.format.response.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomException extends RuntimeException{
    ErrorCode errorCode;
}
