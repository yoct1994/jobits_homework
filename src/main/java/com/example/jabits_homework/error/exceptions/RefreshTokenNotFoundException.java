package com.example.jabits_homework.error.exceptions;

import com.example.jabits_homework.error.exception.BusinessException;
import com.example.jabits_homework.error.exception.ErrorCode;

public class RefreshTokenNotFoundException extends BusinessException {
    public RefreshTokenNotFoundException() {
        super(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }
}
