package com.toy.matcherloper.matching.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Arrays;

@Configuration
public class FirebaseConfig {

    @Bean
    public GoogleCredentials googleCredentials() throws IOException {
        String firebaseConfigPath = "firebase/firebase_service_key.json";
        return GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
    }

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        String firebaseConfigPath = "firebase/firebase_service_key.json";
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials
                        .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                        .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform")))
                .build();


        return FirebaseApp.initializeApp(options);
    }
}
