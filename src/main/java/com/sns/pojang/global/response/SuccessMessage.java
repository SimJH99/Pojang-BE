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
    FIND_ADDRESS_SUCCESS("주소 조회에 성공하였습니다."),
    UPDATE_ADDRESS_SUCCESS("주소 수정에 성공하였습니다."),
    UPDATE_MY_INFO_SUCCESS("회원정보 수정에 성공하였습니다."),
    FIND_MY_INFO_SUCCESS("회원정보 조회에 성공하였습니다."),
    DELETE_MEMBER_SUCCESS("회원 탈퇴되었습니다."),

    // Store
    CREATE_STORE_SUCCESS("매장 신청 되었습니다."),
    UPDATE_STORE_SUCCESS("매장 정보 수정이 성공하였습니다."),
    DELETE_STORE_SUCCESS("매장 삭제를 성공하였습니다."),

    // Favorite
    CREATE_FAVORITE_SUCCESS("찜 완료했습니다."),
    DELETE_FAVORITE_SUCCESS("찜 취소했습니다."),
    COUNT_FAVORITE_SUCCESS("해당 매장의 찜 개수를 조회했습니다."),
    FIND_FAVORITE_SUCCESS("찜한 매장을 조회했습니다.");

    private final String message;
}
