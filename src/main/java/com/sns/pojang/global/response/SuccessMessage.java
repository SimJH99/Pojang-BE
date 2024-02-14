package com.sns.pojang.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {

    // Common
    SEND_CERTIFICATION_SUCCESS("인증 키를 성공적으로 전송하였습니다"),
    VERIFY_CERTIFICATION_SUCCESS("인증 코드 검증에 성공하였습니다."),

    // Member
    CREATE_MEMBER_SUCCESS("회원가입에 성공하였습니다."),
    LOGIN_MEMBER_SUCCESS("로그인에 성공하였습니다."),
    MY_INFO_MEMBER_SUCCESS("회원정보조회에 성공하였습니다."),
    DELETE_MEMBER_SUCCESS("회원 탈퇴되었습니다."),

    // Order
    CREATE_ORDER_SUCCESS("주문이 완료되었습니다.");

    private final String message;
}
