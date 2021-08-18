package com.toy.matcherloper.matching.fcm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirebaseCloudMessageService {

    private static final String FCM_SEND_ENDPOINT = "https://fcm.googleapis.com/v1/projects/toy-matcherloper/messages:send";

    private final GoogleCredentials googleCredentials;
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    public void sendMessageBy(String topic) throws FirebaseMessagingException {
        final Message message = Message.builder()
                .setTopic(topic)
                .build();
        final String response = FirebaseMessaging.getInstance()
                .send(message);
        log.info(response);
    }

    public void sendMessageTo(List<String> targetTokens, String title, String body) throws IOException {
        for (String targetToken : targetTokens) {
            RequestBody requestBody = makeRequestBody(makeMessage(targetToken, title, body));
            final Request request = makeRequest(requestBody);
            Response response = client.newCall(request)
                    .execute();
            log.info(response.body().string());
        }
    }

    private String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(Message.builder()
                        .setToken(targetToken)
                        .setNotification(new Notification(title, body))
                        .build())
                .validateOnly(false)
                .build();
        return objectMapper.writeValueAsString(fcmMessage);
    }

    private RequestBody makeRequestBody(String message) {
        return RequestBody.create(message,
                MediaType.get("application/json; charset=utf-8"));
    }

    private Request makeRequest(RequestBody requestBody) throws IOException {
        return new Request.Builder()
                .url(FCM_SEND_ENDPOINT)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + googleCredentials.getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();
    }
}
