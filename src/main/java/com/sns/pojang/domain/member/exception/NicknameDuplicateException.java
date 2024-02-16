package com.sns.pojang.domain.member.exception;

import com.sns.pojang.global.error.ErrorCode;
import com.sns.pojang.global.error.exception.InvalidValueException;

public class NicknameDuplicateException extends InvalidValueException {
    public NicknameDuplicateException() {
        super(ErrorCode.NICKNAME_DUPLICATION);
    }
}
