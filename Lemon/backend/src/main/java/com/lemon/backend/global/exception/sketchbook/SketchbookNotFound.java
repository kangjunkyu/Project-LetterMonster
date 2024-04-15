package com.lemon.backend.global.exception.sketchbook;

import com.lemon.backend.global.format.response.ErrorCode;

public class SketchbookNotFound extends RuntimeException{

    private final ErrorCode errorCode;

    public SketchbookNotFound() {
        this.errorCode = ErrorCode.SKETCHBOOK_NOT_FOUND;
    }
}
