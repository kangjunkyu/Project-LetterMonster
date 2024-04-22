package com.lemon.backend.global.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class CommonResponseEntity {
    private int statusCode;
    private String message;
    private Object data;

    public static ResponseEntity<CommonResponseEntity> getResponseEntity(SuccessCode sc, Object data) {
        return ResponseEntity
                .status(sc.getStatus())
                .body(CommonResponseEntity.builder()
                        .statusCode(sc.getStatus().value())
                        .message(sc.getMessage())
                        .data(data)
                        .build());
    }

    public static ResponseEntity<CommonResponseEntity> getResponseEntity(SuccessCode sc) {
        return ResponseEntity
                .status(sc.getStatus())
                .body(CommonResponseEntity.builder()
                        .statusCode(sc.getStatus().value())
                        .message(sc.getMessage())
                        .build());
    }
}