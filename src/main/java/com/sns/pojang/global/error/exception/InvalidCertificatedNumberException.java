package com.sns.pojang.global.error.exception;

import com.sns.pojang.global.error.ErrorCode;
import com.sns.pojang.global.error.exception.InvalidValueException;

public class InvalidCertificatedNumberException extends InvalidValueException {
    public InvalidCertificatedNumberException() {
        super(ErrorCode.INVALID_CERTIFICATED_NUMBER);
    }

}
