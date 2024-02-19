package com.sns.pojang.domain.favorite.exception;

import com.sns.pojang.global.error.ErrorCode;
import com.sns.pojang.global.error.exception.InvalidValueException;

public class FavoriteDuplicateException extends InvalidValueException {
    public FavoriteDuplicateException() {
        super(ErrorCode.FAVORITE_DUPLICATION);
    }
}
