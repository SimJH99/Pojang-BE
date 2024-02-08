package com.sns.pojang.global.error.exception;

import com.sns.pojang.global.error.ErrorCode;

public class KeyNotExistException extends InvalidValueException {
    public KeyNotExistException() {
        super(ErrorCode.KEY_NOT_EXIST);
    }
}
