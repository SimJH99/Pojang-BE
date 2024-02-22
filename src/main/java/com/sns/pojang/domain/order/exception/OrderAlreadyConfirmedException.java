package com.sns.pojang.domain.order.exception;

import com.sns.pojang.global.error.ErrorCode;
import com.sns.pojang.global.error.exception.InvalidValueException;

public class OrderAlreadyConfirmedException extends InvalidValueException {
    public OrderAlreadyConfirmedException() {
        super(ErrorCode.ORDER_ALREADY_CONFIRMED);
    }
}
