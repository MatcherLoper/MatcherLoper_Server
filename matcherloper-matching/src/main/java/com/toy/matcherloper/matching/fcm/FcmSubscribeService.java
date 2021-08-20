package com.toy.matcherloper.matching.fcm;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.TopicManagementResponse;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class FcmSubscribeService {

    public String subscribeToTopic(String token, String topic) {
        ApiFuture<TopicManagementResponse> response = FirebaseMessaging
                .getInstance()
                .subscribeToTopicAsync(Arrays.asList(token), topic);
        return response.toString();
    }

    public String unSubscribeToTopic(String token, String topic) {
        ApiFuture<TopicManagementResponse> response = FirebaseMessaging
                .getInstance()
                .unsubscribeFromTopicAsync(Arrays.asList(token), topic);
        return response.toString();
    }

    public String subscribeToTopic(List<String> tokens, String topic) {
        ApiFuture<TopicManagementResponse> response = FirebaseMessaging
                .getInstance()
                .subscribeToTopicAsync(tokens, topic);
        return response.toString();
    }

    public String unSubscribeToTopic(List<String> tokens, String topic) {
        ApiFuture<TopicManagementResponse> response = FirebaseMessaging
                .getInstance()
                .unsubscribeFromTopicAsync(tokens, topic);
        return response.toString();
    }
}
