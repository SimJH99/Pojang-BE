package com.sns.pojang.domain.menu.exception;

import com.sns.pojang.global.error.ErrorCode;
import com.sns.pojang.global.error.exception.EntityNotFoundException;

public class MenuNotFoundException extends EntityNotFoundException {
    public MenuNotFoundException() {
        super(ErrorCode.MENU_NOT_FOUND);
    }
}
