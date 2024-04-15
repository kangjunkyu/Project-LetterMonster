package com.lemon.backend.global.exception.charactermotion;

import com.lemon.backend.global.format.response.ErrorCode;

public class CharacterMotionNotFound extends RuntimeException{

    private final ErrorCode errorCode;

    public CharacterMotionNotFound() {
        this.errorCode = ErrorCode.CHARACTERMOTION_NOT_FOUND;
    }
}
