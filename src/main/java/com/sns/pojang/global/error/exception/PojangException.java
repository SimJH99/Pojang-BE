package com.sns.pojang.global.error.exception;

import com.sns.pojang.global.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PojangException extends RuntimeException{
    private final ErrorCode errorCode;
}
