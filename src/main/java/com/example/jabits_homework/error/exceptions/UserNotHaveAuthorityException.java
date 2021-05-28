package com.example.jabits_homework.error.exceptions;

import com.example.jabits_homework.error.exception.BusinessException;
import com.example.jabits_homework.error.exception.ErrorCode;

public class UserNotHaveAuthorityException extends BusinessException {
    public UserNotHaveAuthorityException() {
        super(ErrorCode.USER_NOT_ADMIN);
    }
}
