package com.lemon.backend.global.exception.motion;

import com.lemon.backend.global.format.response.ErrorCode;

public class MotionNotFound extends RuntimeException{

    private final ErrorCode errorCode;

    public MotionNotFound() {
        this.errorCode = ErrorCode.MOTION_NOT_FOUND;
    }
}
