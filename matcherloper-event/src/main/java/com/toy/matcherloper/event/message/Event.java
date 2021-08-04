package com.toy.matcherloper.event.message;

public abstract class Event {
    private long timestamp;

    public Event(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return this.timestamp;
    }
}
