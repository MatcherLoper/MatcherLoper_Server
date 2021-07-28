package com.toy.matcherloper.web.room.exception;

public class NotFoundRoomException extends RuntimeException {

    public NotFoundRoomException(String message) {
        super(message);
    }

    public NotFoundRoomException(String message, Throwable cause) {
        super(message, cause);
    }
}
