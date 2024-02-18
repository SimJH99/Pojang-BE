package com.sns.pojang.domain.order.exception;

import com.sns.pojang.global.error.ErrorCode;
import com.sns.pojang.global.error.exception.InvalidValueException;

public class InvalidTotalPriceException extends InvalidValueException {
    public InvalidTotalPriceException() {
        super(ErrorCode.INVALID_TOTAL_PRICE);
    }
}
