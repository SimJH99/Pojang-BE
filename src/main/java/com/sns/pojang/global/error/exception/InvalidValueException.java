package com.sns.pojang.global.error.exception;

import com.sns.pojang.global.error.ErrorCode;

public class InvalidValueException extends PojangException {
    public InvalidValueException(ErrorCode errorCode){
        super(errorCode);
    }
}
