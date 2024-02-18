package com.sns.pojang.domain.store.exception;

import com.sns.pojang.global.error.ErrorCode;
import com.sns.pojang.global.error.exception.EntityNotFoundException;
import com.sns.pojang.global.error.exception.InvalidValueException;

public class StoreIdNotEqualException extends InvalidValueException {
    public StoreIdNotEqualException() {
        super(ErrorCode.STORE_ID_NOT_EQUAL);
    }
}
