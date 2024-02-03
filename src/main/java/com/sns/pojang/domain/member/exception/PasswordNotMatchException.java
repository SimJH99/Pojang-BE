package com.sns.pojang.domain.member.exception;

import com.sns.pojang.global.error.ErrorCode;
import com.sns.pojang.global.error.exception.InvalidValueException;

public class PasswordNotMatchException extends InvalidValueException {
    public PasswordNotMatchException() {
        super(ErrorCode.EMAIL_NOT_EXIST);
    }
}
