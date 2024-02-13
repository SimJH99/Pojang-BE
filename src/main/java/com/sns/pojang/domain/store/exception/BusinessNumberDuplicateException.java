package com.sns.pojang.domain.store.exception;

import com.sns.pojang.global.error.ErrorCode;
import com.sns.pojang.global.error.exception.InvalidValueException;

public class BusinessNumberDuplicateException extends InvalidValueException {
    public BusinessNumberDuplicateException() {
        super(ErrorCode.BUSINESS_NUMBER_DUPLICATION);
    }
}
