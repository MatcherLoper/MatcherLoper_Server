package com.toy.matcherloper.event.handler;

import com.toy.matcherloper.event.message.SubscribeEvent;
import com.toy.matcherloper.matching.fcm.FcmSubscribeService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SubscribeEventHandler implements EventHandler<SubscribeEvent> {

    private final FcmSubscribeService fcmSubscribeService;

    @Override
    public void handle(SubscribeEvent event) {
        fcmSubscribeService.subscribeToTopic(event.getToken(), event.getTopic());
    }
}
