package com.toy.matcherloper.core.user.exception;

public class NotMatchedPasswordException extends RuntimeException {
    public NotMatchedPasswordException(String message) {
        super(message);
    }

    public NotMatchedPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
