package com.example.jabits_homework.error.exceptions;

import com.example.jabits_homework.error.exception.BusinessException;
import com.example.jabits_homework.error.exception.ErrorCode;

public class UserAlreadySignedException extends BusinessException {
    public UserAlreadySignedException() {
        super(ErrorCode.USER_ALREADY_SIGNED);
    }
}
