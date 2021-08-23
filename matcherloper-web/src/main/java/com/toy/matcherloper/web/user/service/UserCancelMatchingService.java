package com.toy.matcherloper.web.user.service;

import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.model.type.RoleType;
import com.toy.matcherloper.event.dispatcher.Events;
import com.toy.matcherloper.event.handler.UnSubscribeEventHandler;
import com.toy.matcherloper.event.message.UnSubscribeEvent;
import com.toy.matcherloper.matching.fcm.FcmSubscribeService;
import com.toy.matcherloper.matching.type.TopicType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCancelMatchingService {

    private final UserFindService userFindService;
    private final FcmSubscribeService fcmSubscribeService;

    @Transactional
    public Long cancelMatching(Long userId) {
        final User user = userFindService.findById(userId);
        Events.handleAsync(new UnSubscribeEventHandler(fcmSubscribeService));
        user.changeStatusMatching(RoleType.NONE);
        Events.raise(new UnSubscribeEvent(user.getDeviceToken(), TopicType.MATCHING.getToken()));
        return user.getId();
    }
}
