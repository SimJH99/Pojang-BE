package com.sns.pojang.domain.store.exception;

import com.sns.pojang.global.error.ErrorCode;
import com.sns.pojang.global.error.exception.InvalidValueException;

public class AlreadyOpenedException extends InvalidValueException {
    public AlreadyOpenedException() {
        super(ErrorCode.ALREADY_OPEN_STORE);
    }
}
