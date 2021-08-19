package com.toy.matcherloper.web.user.exception;

public class UserRoomNotFoundException extends RuntimeException {

    public UserRoomNotFoundException() {
        super();
    }

    public UserRoomNotFoundException(String message) {
        super(message);
    }
}
