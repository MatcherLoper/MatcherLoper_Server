package com.toy.matcherloper.core.room.model;

public enum RoomStatus {
    OPEN("방 열려있음"), CLOSED("방 닫혀있음"), FULL("방 꽉참"), START("프로젝트 시작");

    private final String detail;

    RoomStatus(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }
}
