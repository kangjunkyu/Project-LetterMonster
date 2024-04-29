package com.lemon.backend.global.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class ErrorResponseEntity {
    private int status;
    private String message;

    public static ResponseEntity<ErrorResponseEntity> toResponseEntity(ErrorCode ec){
        return ResponseEntity
                .status(ec.getStatus())
                .body(ErrorResponseEntity.builder()
                        .status(ec.getStatus().value())
                        .message(ec.getMessage())
                        .build());
    }
}
