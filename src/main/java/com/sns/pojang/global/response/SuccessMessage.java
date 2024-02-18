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

    // Menu
    CREATE_MENU_SUCCESS("메뉴가 등록되었습니다."),
    CREATE_MENU_OPTION_GROUP_SUCCESS("메뉴 옵션 그룹이 등록되었습니다."),
    UPDATE_MENU_SUCCESS("메뉴가 수정되었습니다."),
    DELETE_MENU_SUCCESS("메뉴가 삭제되었습니다."),
    GET_MENUS_SUCCESS("메뉴 목록을 조회하였습니다."),

    // Order
    CREATE_ORDER_SUCCESS("주문이 완료되었습니다."),

    // Store
    REGISTER_BUSINESS_NUMBER_SUCCESS("사업자 등록번호가 등록되었습니다."),
    CREATE_STORE_SUCCESS("매장이 등록 되었습니다."),
    UPDATE_STORE_SUCCESS("매장 정보를 수정하였습니다."),
    DELETE_STORE_SUCCESS("매장을 삭제하였습니다.");

    private final String message;
}
