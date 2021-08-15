package com.toy.matcherloper.event.handler;

import com.toy.matcherloper.event.message.UserJoinRoomEvent;
import com.toy.matcherloper.web.user.service.UserJoinRoomService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserJoinRoomHandler implements EventHandler<UserJoinRoomEvent> {

    private final UserJoinRoomService userJoinRoomService;

    @Override
    public void handle(UserJoinRoomEvent event) {
        userJoinRoomService.join(event.getUserId(), event.getPosition());
    }
}
