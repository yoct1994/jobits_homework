package com.example.jabits_homework.error.exceptions;

import com.example.jabits_homework.error.exception.BusinessException;
import com.example.jabits_homework.error.exception.ErrorCode;

public class IsNotRefreshTokenException extends BusinessException {
    public IsNotRefreshTokenException() {
        super(ErrorCode.IS_NOT_REFRESH_TOKEN);
    }
}
