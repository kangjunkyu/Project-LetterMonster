package com.lemon.backend.global.exception.character;

import com.lemon.backend.global.format.response.ErrorCode;

public class CharacterNotFound extends RuntimeException{

    private final ErrorCode errorCode;

    public CharacterNotFound() {
        this.errorCode = ErrorCode.CHARACTER_NOT_FOUND;
    }
}
