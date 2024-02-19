package com.sns.pojang.domain.menu.exception;

import com.sns.pojang.global.error.ErrorCode;
import com.sns.pojang.global.error.exception.EntityNotFoundException;

public class MenuOptionGroupNotFoundException extends EntityNotFoundException {
    public MenuOptionGroupNotFoundException() {
        super(ErrorCode.MENU_OPTION_GROUP_NOT_FOUND);
    }
}
