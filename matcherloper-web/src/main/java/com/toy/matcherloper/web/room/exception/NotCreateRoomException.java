package com.toy.matcherloper.web.room.exception;

public class NotCreateRoomException extends RuntimeException{

    public NotCreateRoomException(String message) {
        super(message);
    }

    public NotCreateRoomException(String message, Throwable cause) {
        super(message, cause);
    }
}
