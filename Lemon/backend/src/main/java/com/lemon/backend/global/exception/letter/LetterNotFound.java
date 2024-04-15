package com.lemon.backend.global.exception.letter;

import com.lemon.backend.global.format.response.ErrorCode;

public class LetterNotFound extends RuntimeException{

    private final ErrorCode errorCode;

    public LetterNotFound() {
        this.errorCode = ErrorCode.LETTER_NOT_FOUND;
    }
}
