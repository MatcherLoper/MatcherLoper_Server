package com.toy.matcherloper.matching.fcm;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.TopicManagementResponse;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class FcmSubscribeService {

    private static final String TOPIC = "matching";

    public String subscribeToTopic(String token) {
        ApiFuture<TopicManagementResponse> response = FirebaseMessaging
                .getInstance()
                .subscribeToTopicAsync(Arrays.asList(token), TOPIC);
        return response.toString();
    }

    public String unSubscribeToTopic(String token) {
        ApiFuture<TopicManagementResponse> response = FirebaseMessaging
                .getInstance()
                .unsubscribeFromTopicAsync(Arrays.asList(token), TOPIC);
        return response.toString();
    }
}
