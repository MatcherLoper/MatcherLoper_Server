package com.toy.matcherloper.matching.fcm;

import com.google.api.client.util.Key;
import com.google.firebase.messaging.Message;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FcmMessage {

    @Key(value = "validate_onle")
    private boolean validateOnly;

    @Key(value = "message")
    private Message message;

    @Builder
    public FcmMessage(boolean validateOnly, Message message) {
        this.validateOnly = validateOnly;
        this.message = message;
    }
}
