package com.ig.amr.config.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "INVALID INPUT VALUE"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "RESOURCE NOT FOUND"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR"),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED ACCESS"),
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "FORBIDDEN ACCESS"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "METHOD NOT ALLOWED");


    private final HttpStatus status;
    private final String message;
}