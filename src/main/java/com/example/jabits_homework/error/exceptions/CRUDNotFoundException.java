package com.example.jabits_homework.error.exceptions;

import com.example.jabits_homework.error.exception.BusinessException;
import com.example.jabits_homework.error.exception.ErrorCode;

public class CRUDNotFoundException extends BusinessException {
    public CRUDNotFoundException() {
        super(ErrorCode.CRUD_NOT_FOUND);
    }
}
