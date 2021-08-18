package com.toy.matcherloper.matching.process;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.toy.matcherloper.matching.fcm.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmMatchingSystem implements MatchingSystem {

    private final FirebaseCloudMessageService messageService;

    @Override
    public void matching(String topic, String title) {
        try {
            messageService.sendMessageBy(topic, title, null);
        } catch (FirebaseMessagingException e) {
            log.error(e.getMessage());
        }
    }
}
