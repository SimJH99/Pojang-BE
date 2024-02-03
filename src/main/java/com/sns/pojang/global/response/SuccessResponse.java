package com.sns.pojang.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SuccessResponse<T> {
    private int status;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    // 어떤 데이터 타입도 처리할 수 있도록 제네릭 사용
    private T result;

    public static <T> SuccessResponse<T> create(int status, String message) {
        return new SuccessResponse<>(status, message, null);
    }

    public static <T> SuccessResponse<T> create(int status, String message, T result) {
        return new SuccessResponse<>(status, message, result);
    }
}
