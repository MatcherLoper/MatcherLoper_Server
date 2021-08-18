package com.toy.matcherloper.auth.oauth2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    public BadRequestException(String msg) {
        super(msg);
    }

    public BadRequestException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}

