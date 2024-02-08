package com.sns.pojang.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {
    // Member
    CREATE_MEMBER_SUCCESS("회원가입에 성공하였습니다."),
    LOGIN_MEMBER_SUCCESS("로그인에 성공하였습니다."),
    MY_INFO_MEMBER_SUCCESS("회원정보조회에 성공하였습니다."),
    DELETE_MEMBER_SUCCESS("회원 탈퇴되었습니다.");

    private final String message;
}
