package com.toy.matcherloper.event.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RoomCreateEvent extends Event {

    private final String message;
    private final String token;
}
