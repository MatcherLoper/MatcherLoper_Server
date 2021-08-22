package com.toy.matcherloper.event.handler;

import com.toy.matcherloper.event.message.UnSubscribeEvent;
import com.toy.matcherloper.matching.fcm.FcmSubscribeService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UnSubscribeEventHandler implements EventHandler<UnSubscribeEvent> {

    private final FcmSubscribeService fcmSubscribeService;

    @Override
    public void handle(UnSubscribeEvent event) {
        fcmSubscribeService.unSubscribeToTopic(event.getToken(), event.getTopic());
    }
}
