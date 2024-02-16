package com.sns.pojang.domain.favorite.exception;

import com.sns.pojang.global.error.ErrorCode;
import com.sns.pojang.global.error.exception.EntityNotFoundException;

public class FavoriteNotFoundException extends EntityNotFoundException {
    public FavoriteNotFoundException() {
        super(ErrorCode.FAVORITE_NOT_FOUND);
    }
}
