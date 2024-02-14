package com.sns.pojang.domain.store.exception;

import com.sns.pojang.global.error.ErrorCode;
import com.sns.pojang.global.error.exception.EntityNotFoundException;

public class BusinessNumberNotFoundException extends EntityNotFoundException {
    public BusinessNumberNotFoundException(){super(ErrorCode.BUSINESS_NUMBER_NOTFOUND);}
}
