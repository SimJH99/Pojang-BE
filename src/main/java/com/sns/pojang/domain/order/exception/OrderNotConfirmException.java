package com.sns.pojang.domain.order.exception;

import com.sns.pojang.global.error.ErrorCode;
import com.sns.pojang.global.error.exception.EntityNotFoundException;

public class OrderNotConfirmException extends EntityNotFoundException {
    public OrderNotConfirmException() {
        super(ErrorCode.ORDER_NOT_CONFIRM);
    }
}
