package com.sns.pojang.domain.order.exception;

import com.sns.pojang.global.error.ErrorCode;
import com.sns.pojang.global.error.exception.InvalidValueException;

public class OrderAlreadyCanceledException extends InvalidValueException {
    public OrderAlreadyCanceledException() {
        super(ErrorCode.ORDER_ALREADY_CANCELED);
    }
}
