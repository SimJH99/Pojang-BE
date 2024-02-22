package com.sns.pojang.domain.member.exception;

import com.sns.pojang.global.error.ErrorCode;
import com.sns.pojang.global.error.exception.InvalidValueException;

public class PhoneNumberDuplicateException extends InvalidValueException {
    public PhoneNumberDuplicateException() {
        super(ErrorCode.PHONE_NUMBER_DUPLICATION);
    }
}
