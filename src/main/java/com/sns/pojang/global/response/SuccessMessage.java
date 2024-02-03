package com.sns.pojang.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {
    // Member
    CREATE_MEMBER_SUCCESS("회원가입에 성공하였습니다."),
    LOGIN_MEMBER_SUCCESS("로그인에 성공하였습니다.");

    private final String message;
}
