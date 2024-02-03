package com.sns.pojang.global.error.exception;

import com.sns.pojang.global.error.ErrorCode;

public class EntityNotFoundException extends PojangException{
    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
