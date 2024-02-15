package com.sns.pojang.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE( 400, "입력값이 유효하지 않습니다."),
    METHOD_NOT_ALLOWED(405, "클라이언트가 사용한 HTTP 메서드가 리소스에서 허용되지 않습니다."),
    INTERNAL_SERVER_ERROR( 500, "서버에서 요청을 처리하는 동안 오류가 발생했습니다."),
    ACCESS_DENIED(403, "접근 권한이 없습니다."),
    KEY_NOT_EXIST(400, "존재하지 않는 키입니다."),
    INVALID_CERTIFICATED_NUMBER(400, "인증 번호가 다릅니다."),

    // Member
    EMAIL_DUPLICATION(400, "이미 존재하는 이메일입니다."),
    MEMBER_NOT_FOUND(400, "존재하지 않는 회원입니다."),
    PASSWORD_NOT_MATCH(400, "비밀번호가 일치하지 않습니다."),
    NICKNAME_DUPLICATION( 400, "이미 존재하는 닉네임입니다."),

    // Store
    BUSINESS_NUMBER_DUPLICATION(400, "이미 등록된 사업자 번호입니다."), 
    BUSINESS_NUMBER_NOT_FOUND(400, "존재하지 않는 사업자 번호입니다."),
    STORE_NOT_FOUND(400, "등록된 매장이 아닙니다.");

    private int status;
    private String message;

    ErrorCode(final int status, final String message){
        this.message = message;
        this.status = status;
    }
}
