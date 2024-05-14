package com.lemon.backend.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //기타
    INVALID_ACCESS(HttpStatus.BAD_REQUEST, "잘못된 접근이거나 권한이 없습니다."),
    NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "찾을 수 없습니다."),
    CANT_USING_BAD_WORD(HttpStatus.BAD_REQUEST, "닉네임에 욕설이 포함되어 있어요. 다른 닉네임으로 설정해주세요"),

    //스케치북
    SKETCHBOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "스케치북 정보를 찾을 수 없습니다."),
    
    //즐겨찾기
    FAVORITE_SKETCHBOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "즐겨찾기 내 스케치북 정보를 찾을 수 없습니다."),
    ALREADY_FAVORITE_SKECHBOOK(HttpStatus.NOT_FOUND, "이미 추가한 스케치북 입니다.."),

    //편지
    LETTER_NOT_FOUND(HttpStatus.NOT_FOUND, "편지 정보를 찾을 수 없습니다."),

    //회원
    USERS_NOT_FOUND(HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다."),
    INVALID_USER_NICKNAME(HttpStatus.BAD_REQUEST, "닉네임이 공백이거나 특수문자가 포함되어 있습니다."),

    //캐릭터
    CHARACTER_NOT_FOUND(HttpStatus.NOT_FOUND, "캐릭터 정보를 찾을 수 없습니다."),
    CHARACTER_SAVE_FAILED(HttpStatus.EXPECTATION_FAILED, "캐릭터 저장에 실패했습니다." ),

    //캐릭터모션
    CHARACTERMOTION_NOT_FOUND(HttpStatus.NOT_FOUND, "캐릭터 모션 정보를 찾을 수 없습니다."),
    CHARACTERMOTION_SAVE_FAILED(HttpStatus.EXPECTATION_FAILED, "캐릭터 모션 저장에 실패했습니다." ),

    //모션
    MOTION_NOT_FOUND(HttpStatus.NOT_FOUND, "모션 정보를 찾을 수 없습니다."),

    //유저
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    IS_WITHDRAW_USER(HttpStatus.BAD_REQUEST, "회원 탈퇴한 유저입니다"),
    EXPIRED_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    NOT_FOUND_AUTH_TOKEN(HttpStatus.BAD_REQUEST, "토큰 정보가 없습니다."),
    UNAUTHORIZED_FUNCTION_ACCESS(HttpStatus.UNAUTHORIZED, "로그인 후 이용할 수 있습니다."),
    BLACK_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "블랙 리스트에 추가되어 만료된 토큰입니다."),
    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "권한 정보가 없는 토큰입니다."),

    //그룹
    NOT_FOUND_GROUP(HttpStatus.NOT_FOUND, "그룹을 찾을 수 없습니다."),

    //알림
    NOT_FOUND_NOTIFICATION(HttpStatus.NOT_FOUND, "알림 정보를 찾을 수 없습니다."),

    //친구
    CAN_NOT_ADD_FRIEND(HttpStatus.BAD_REQUEST, "이미 등록된 친구입니다."),

    //트래픽
    TOO_MANY_REQUEST(HttpStatus.TOO_MANY_REQUESTS, "요청 횟수를 초과했습니다.");

    private final HttpStatus status;
    private final String message;

}
