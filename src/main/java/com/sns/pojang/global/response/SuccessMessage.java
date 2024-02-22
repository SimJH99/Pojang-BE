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
    VERIFY_EMAIL_SUCCESS("사용 가능한 이메일입니다."),
    VERIFY_NICKNAME_SUCCESS("사용 가능한 닉네임입니다."),
    LOGIN_MEMBER_SUCCESS("로그인에 성공하였습니다."),
    FIND_ADDRESS_SUCCESS("주소 조회에 성공하였습니다."),
    UPDATE_ADDRESS_SUCCESS("주소 수정에 성공하였습니다."),
    UPDATE_MY_INFO_SUCCESS("회원정보 수정에 성공하였습니다."),
    FIND_MY_INFO_SUCCESS("회원정보 조회에 성공하였습니다."),
    DELETE_MEMBER_SUCCESS("회원 탈퇴되었습니다."),

    // Menu
    CREATE_MENU_SUCCESS("메뉴가 등록되었습니다."),
    CREATE_MENU_OPTION_GROUP_SUCCESS("메뉴 옵션 그룹이 등록되었습니다."),
    CREATE_MENU_OPTION_SUCCESS("메뉴 옵션이 등록되었습니다."),
    UPDATE_MENU_SUCCESS("메뉴가 수정되었습니다."),
    DELETE_MENU_SUCCESS("메뉴가 삭제되었습니다."),
    GET_MENUS_SUCCESS("메뉴 목록을 조회하였습니다."),
    GET_MENU_DETAIL_SUCCESS("메뉴를 상세 조회합니다."),
    GET_MENU_OPTION_SUCCESS("메뉴 옵션을 조회합니다."),

    // Order
    CREATE_ORDER_SUCCESS("주문이 완료되었습니다."),
    CANCEL_MEMBER_ORDER_SUCCESS("주문을 취소하였습니다."),
    CANCEL_STORE_ORDER_SUCCESS("고객의 주문을 취소하였습니다."),
    ACCEPT_ORDER_SUCCESS("고객의 주문을 접수하였습니다."),
    CONFIRM_ORDER_SUCCESS("고객의 주문을 확정하였습니다."),
    GET_ORDER_DETAIL_SUCCESS("주문 상세를 조회합니다."),
    GET_ORDERS_SUCCESS("주문 목록을 조회하였습니다."),
    GET_MY_ORDERS_SUCCESS("나의 목록을 조회하였습니다."),

    // Store
    SEARCH_MY_STORE_SUCCESS("내 매장보기를 성공하였습니다."),
    SEARCH_STORE_SUCCESS("매장 조회를 완료했습니다."),
    SEARCH_STORE_ORDERS_SUCCESS("매장의 전체 주문 목록을 조회합니다."),
    REGISTER_BUSINESS_NUMBER_SUCCESS("사업자 등록번호가 등록되었습니다."),
    CREATE_STORE_SUCCESS("매장이 등록 되었습니다."),
    UPDATE_STORE_SUCCESS("매장 정보를 수정하였습니다."),
    DELETE_STORE_SUCCESS("매장을 삭제하였습니다."),
    FIND_REVIEW_SUCCESS("매장 리뷰를 조회했습니다."),

    // Favorite
    CREATE_FAVORITE_SUCCESS("찜 완료했습니다."),
    DELETE_FAVORITE_SUCCESS("찜 취소했습니다."),
    COUNT_FAVORITE_SUCCESS("해당 매장의 찜 개수를 조회했습니다."),
    FIND_FAVORITE_SUCCESS("찜한 매장을 조회했습니다."),
    FIND_RATING_SUCCESS("별점을 조회했습니다."),

    // Review
    CREATE_REVIEW_SUCCESS("리뷰를 작성했습니다."),
    UPDATE_REVIEW_SUCCESS("리뷰를 수정했습니다."),
    DELETE_REVIEW_SUCCESS("리뷰를 삭제했습니다.");

    private final String message;
}
