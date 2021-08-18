package com.toy.matcherloper.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "auth.properties.key")
public class AuthProperties {
    private final Auth auth;
    private final OAuth2 oAuth2;

    @Getter
    @RequiredArgsConstructor
    @ConstructorBinding
    public static class Auth {
        private final String tokenSecret;
        private final long tokenExpirationMsec;
    }

    @Getter
    @RequiredArgsConstructor
    @ConstructorBinding
    public static class OAuth2 {
        private final List<String> authorizedRedirectUris;
    }
}
