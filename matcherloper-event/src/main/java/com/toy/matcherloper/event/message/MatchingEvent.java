package com.toy.matcherloper.event.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class MatchingEvent extends Event {

    private final List<String> tokens;
    private final String message;
}
