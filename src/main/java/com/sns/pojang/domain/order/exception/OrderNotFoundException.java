package com.sns.pojang.domain.order.exception;

import com.sns.pojang.global.error.ErrorCode;
import com.sns.pojang.global.error.exception.EntityNotFoundException;

public class OrderNotFoundException extends EntityNotFoundException {
    public OrderNotFoundException() {
        super(ErrorCode.ORDER_NOT_FOUND);
    }
}
