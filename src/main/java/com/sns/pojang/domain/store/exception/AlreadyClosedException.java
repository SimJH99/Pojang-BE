package com.sns.pojang.domain.store.exception;

import com.sns.pojang.global.error.ErrorCode;
import com.sns.pojang.global.error.exception.InvalidValueException;

public class AlreadyClosedException extends InvalidValueException {
    public AlreadyClosedException() {
        super(ErrorCode.ALREADY_CLOSE_STORE);
    }
}
