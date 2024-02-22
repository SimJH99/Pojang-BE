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
    EMAIL_NOT_FOUND(400, "존재하지 않는 이메일입니다."),
    MEMBER_NOT_FOUND(400, "존재하지 않는 회원입니다."),
    PASSWORD_NOT_MATCH(400, "비밀번호가 일치하지 않습니다."),
    NICKNAME_DUPLICATION( 400, "이미 존재하는 닉네임입니다."),
    PHONE_NUMBER_DUPLICATION( 400, "이미 존재하는 휴대폰 번호입니다."),

    // Store
    BUSINESS_NUMBER_DUPLICATION(400, "이미 등록된 사업자 번호입니다."), 
    BUSINESS_NUMBER_NOT_FOUND(400, "존재하지 않는 사업자 번호입니다."),
    STORE_NOT_FOUND(400, "등록된 매장이 아닙니다."),
    MY_STORE_NOT_FOUND(400, "등록한 매장이 없습니다."),
    NOT_INVALID_VALUE_MEMBER(400, "회원과 매장이 일치하지 않습니다."),
    STORE_ID_NOT_EQUAL(400, "메뉴의 storeId와 입력 storeId가 일치하지 않습니다."),

    // Favorite
    FAVORITE_NOT_FOUND(400, "존재하지 않는 찜입니다."),
    FAVORITE_DUPLICATION(400, "이미 찜한 가게입니다."),

    // Review
    REVIEW_DUPLICATION(400, "이미 등록된 리뷰입니다."),
    REVIEW_NOT_FOUND(400, "등록된 리뷰가 없습니다."),

    // Order
    INVALID_TOTAL_PRICE(400, "최종 주문 금액이 일치하지 않습니다."),
    ORDER_NOT_CONFIRM(400, "확정되지 않은 주문입니다."),
    ORDER_NOT_FOUND(400, "존재하지 않는 주문입니다."),
    ORDER_ALREADY_CANCELED(400, "이미 취소된 주문입니다."),
    CANNOT_CONFIRM_ORDER(400, "주문 접수인 상태에서만 확정할 수 있습니다."),
    CANNOT_ACCEPT_ORDER(400, "주문 대기인 상태에서만 접수할 수 있습니다."),
    CANNOT_CANCEL_ORDER(400, "주문 대기인 상태에서만 취소할 수 있습니다."),
    ORDER_ALREADY_CONFIRMED(400, "이미 확정된 주문입니다."),
    ORDER_ALREADY_ORDERED(400, "이미 접수된 주문입니다."),
    MEMBER_ORDER_MISMATCH(403, "주문자 정보가 일치하지 않습니다."),
    STORE_MENU_MISMATCH(400, "가게의 메뉴와 입력된 메뉴가 일치하지 않습니다."),
    STORE_ORDER_MISMATCH(400, "주문의 가게와 입력된 가게가 일치하지 않습니다."),
  
    // Menu
    MENU_NOT_FOUND(400, "메뉴를 찾을 수 없습니다."),
    MENU_OPTION_NOT_FOUND(400, "메뉴 옵션을 찾을 수 없습니다."),
    MENU_OPTION_GROUP_NOT_FOUND(400, "메뉴 옵션 그룹을 찾을 수 없습니다."),
    MENU_ID_NOT_EQUAL(400, "메뉴 옵션 그룹의 menuId와 입력된 menuId가 일치하지 않습니다."),

    //Image
    IMAGE_INVALID_VALUE(400, "사용가능한 이미지 파일이 아닙니다.");

    private int status;
    private String message;

    ErrorCode(final int status, final String message){
        this.message = message;
        this.status = status;
    }
}
