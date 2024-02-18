package com.sns.pojang.domain.menu.exception;

import com.sns.pojang.global.error.ErrorCode;
import com.sns.pojang.global.error.exception.InvalidValueException;

public class MenuIdNotEqualException extends InvalidValueException {
    public MenuIdNotEqualException() {
        super(ErrorCode.MENU_ID_NOT_EQUAL);
    }
}
