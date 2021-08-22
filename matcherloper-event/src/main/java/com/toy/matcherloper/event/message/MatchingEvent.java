package com.toy.matcherloper.event.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MatchingEvent extends Event {

    private final String topic;
    private final String title;
}
