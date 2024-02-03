package com.sns.pojang.domain.member.exception;

import com.sns.pojang.global.error.ErrorCode;
import com.sns.pojang.global.error.exception.InvalidValueException;

public class EmailDuplicateException extends InvalidValueException {
    public EmailDuplicateException() {
        super(ErrorCode.EMAIL_DUPLICATION);
    }
}
