package com.lemon.backend.global.exception.users;

import com.lemon.backend.global.format.response.ErrorCode;

public class UsersNotFound extends RuntimeException{

    private final ErrorCode errorCode;

    public UsersNotFound() {
        this.errorCode = ErrorCode.USERS_NOT_FOUND;
    }
}
