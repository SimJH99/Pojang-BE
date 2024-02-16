package com.sns.pojang.domain.store.exception;

import com.sns.pojang.global.error.ErrorCode;
import com.sns.pojang.global.error.exception.EntityNotFoundException;

public class StoreNotFoundException extends EntityNotFoundException {
    public StoreNotFoundException() {
        super(ErrorCode.STORE_NOT_FOUND);
    }
}
