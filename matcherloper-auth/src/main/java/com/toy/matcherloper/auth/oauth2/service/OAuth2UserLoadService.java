package com.toy.matcherloper.auth.oauth2.service;

import com.toy.matcherloper.auth.oauth2.exception.OAuth2AuthenticationProcessingException;
import com.toy.matcherloper.auth.oauth2.info.OAuth2UserInfo;
import com.toy.matcherloper.auth.oauth2.info.OAuth2UserInfoFactory;
import com.toy.matcherloper.auth.security.model.UserPrincipal;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.model.type.AuthProviderType;
import com.toy.matcherloper.core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OAuth2UserLoadService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            return extractOAuthUserFromRequest(userRequest, oAuth2User);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    //사용자 정보 추출
    private OAuth2User extractOAuthUserFromRequest(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory
                .getOAuth2UserInfo(registrationId, oAuth2User.getAttributes());

        if (oAuth2UserInfo.getEmail().isEmpty()) {
            throw new OAuth2AuthenticationProcessingException("OAuth2 공급자(구글, 네이버 등)에서 이메일을 찾을 수 없습니다.");
        }

        Optional<User> userByEmail = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        User user;

        if (userByEmail.isPresent()) {
            user = userByEmail.get();

            if (!user.getAuthProvider().equals(AuthProviderType.valueOf(registrationId))) {
                throw new OAuth2AuthenticationProcessingException(
                        user.getAuthProvider() + "계정을 사용하기 위해서 로그인이 필요합니다.");
            }

            user = updateUser(user, oAuth2UserInfo);
        } else {
            user = registerUserFromRequest(userRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(user);
    }

    private User registerUserFromRequest(OAuth2UserRequest userRequest, OAuth2UserInfo oAuth2UserInfo) {
        return userRepository.save(
                User.builder()
                        .email(oAuth2UserInfo.getEmail())
                        .name(oAuth2UserInfo.getName())
                        .provider(AuthProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId()))
                        .providerId(oAuth2UserInfo.getId())
                        .build()
        );
    }

    private User updateUser(User user, OAuth2UserInfo oAuth2UserInfo) {
        return userRepository.save(
                user.update(oAuth2UserInfo.getName())
        );
    }
}
