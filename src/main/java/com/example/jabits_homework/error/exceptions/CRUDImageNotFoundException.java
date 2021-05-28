package com.example.jabits_homework.error.exceptions;

import com.example.jabits_homework.error.exception.BusinessException;
import com.example.jabits_homework.error.exception.ErrorCode;

public class CRUDImageNotFoundException extends BusinessException {
    public CRUDImageNotFoundException() {
        super(ErrorCode.CRUD_IMAGE_NOT_FOUND);
    }
}
