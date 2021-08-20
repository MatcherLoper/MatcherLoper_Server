package com.toy.matcherloper.event.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UnSubscribeEvent extends Event{

    private final String token;
    private final String topic;
}
