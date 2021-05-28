package com.example.jabits_homework.error.exceptions;

import com.example.jabits_homework.error.exception.BusinessException;
import com.example.jabits_homework.error.exception.ErrorCode;

public class BadExRequestException extends BusinessException {
    public BadExRequestException() {
        super(ErrorCode.BAD_EX_REQUEST);
    }
}
