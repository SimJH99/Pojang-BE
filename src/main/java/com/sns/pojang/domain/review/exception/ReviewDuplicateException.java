package com.sns.pojang.domain.review.exception;

import com.sns.pojang.global.error.ErrorCode;
import com.sns.pojang.global.error.exception.InvalidValueException;

public class ReviewDuplicateException extends InvalidValueException {
    public ReviewDuplicateException() {
        super(ErrorCode.REVIEW_DUPLICATION);
    }
}
