package com.example.jabits_homework.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DECODE_FAIL(400, "password decode fail"),
    USER_NOT_ADMIN(403, "user don't have ADMIN authority"),
    USER_NOT_FOUND(403, "user not found"),
    USER_ALREADY_SIGNED(403, "user already signed"),
    LOGIN_FAIL(403, "login fail"),
    INVALID_TOKEN(403, "invalid token"),
    REFRESH_TOKEN_NOT_FOUND(404, "refresh_token not found"),
    CRUD_NOT_FOUND(404, "list not found"),
    CRUD_IMAGE_NOT_FOUND(404, "list image not found"),
    IS_NOT_REFRESH_TOKEN(404, "is not refresh token"),
    BAD_EX_REQUEST(400, "bad request ex"),
    TOKEN_EXPIRED(403, "token expired");

    private int status;
    private String message;
}
