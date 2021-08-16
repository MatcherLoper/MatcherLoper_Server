package com.toy.matcherloper.auth.oauth2.info;

import com.toy.matcherloper.auth.oauth2.exception.OAuth2AuthenticationProcessingException;
import com.toy.matcherloper.core.user.model.type.AuthProviderType;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProviderType.GOOGLE.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException(registrationId + " 로그인은 지원하지 않습니다.");
        }

    }
}
