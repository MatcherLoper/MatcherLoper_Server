package com.toy.matcherloper.matching.process;

import com.toy.matcherloper.matching.fcm.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmMatchingSystem implements MatchingSystem {

    private final FirebaseCloudMessageService messageService;

    @Override
    public void matching(List<String> tokens, String message) {
        try {
            messageService.sendMessageTo(tokens, message, null);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
