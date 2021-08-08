package com.toy.matcherloper.web.room.exception;

public class RoomNotCreateException extends RuntimeException{

    public RoomNotCreateException(String message) {
        super(message);
    }

    public RoomNotCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}
