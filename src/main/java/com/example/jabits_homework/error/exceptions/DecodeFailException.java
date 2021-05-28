package com.example.jabits_homework.error.exceptions;

import com.example.jabits_homework.error.exception.BusinessException;
import com.example.jabits_homework.error.exception.ErrorCode;

public class DecodeFailException extends BusinessException {
    public DecodeFailException() {
        super(ErrorCode.DECODE_FAIL);
    }
}
