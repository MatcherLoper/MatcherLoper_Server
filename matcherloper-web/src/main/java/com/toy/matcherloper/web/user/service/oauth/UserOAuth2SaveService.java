package com.toy.matcherloper.web.user.service.oauth;

import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.model.type.AuthProviderType;
import com.toy.matcherloper.core.user.model.type.RoleType;
import com.toy.matcherloper.core.user.repository.UserRepository;
import com.toy.matcherloper.web.user.api.dto.request.OAuth2FirstLoginRequest;
import com.toy.matcherloper.web.user.exception.EmailDuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserOAuth2SaveService {

    private final UserRepository userRepository;

    @Transactional
    public Long saveOAuth2User(OAuth2FirstLoginRequest oAuth2FirstLoginRequest) {
        checkDuplicatedEmail(oAuth2FirstLoginRequest.getEmail());
        User user = User.createFromOAuth2(
                oAuth2FirstLoginRequest.getEmail(),
                oAuth2FirstLoginRequest.getName(),
                oAuth2FirstLoginRequest.getDeviceToken(),
                AuthProviderType.google,
                RoleType.NONE
        );
        userRepository.save(user);
        return user.getId();
    }

    private void checkDuplicatedEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailDuplicateException(String.format("%s is duplicated email", email));
        }
    }
}
