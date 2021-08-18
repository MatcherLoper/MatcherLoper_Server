package com.toy.matcherloper.event.handler;

import com.toy.matcherloper.event.message.MatchingEvent;
import com.toy.matcherloper.matching.process.MatchingSystem;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MatchingEventHandler implements EventHandler<MatchingEvent> {

    private final MatchingSystem matchingSystem;

    @Override
    public void handle(MatchingEvent event) {
        matchingSystem.matching(event.getTopic(), event.getTitle());
    }
}
