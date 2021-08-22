package com.toy.matcherloper.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.google")
public class OAuth2Properties {

    private final String clientId;
    private final String clientSecret;
}
