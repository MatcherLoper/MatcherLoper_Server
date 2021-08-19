package com.toy.matcherloper.web.user.service;

import com.toy.matcherloper.core.user.exception.PasswordNotMatchedException;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.model.type.AuthProviderType;
import com.toy.matcherloper.core.user.repository.UserRepository;
import com.toy.matcherloper.web.user.api.dto.request.SignInRequest;
import com.toy.matcherloper.web.user.api.dto.request.SignUpRequest;
import com.toy.matcherloper.web.user.api.dto.response.SignInResponse;
import com.toy.matcherloper.web.user.exception.EmailDuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.toy.matcherloper.web.utils.DtoConverter.*;

@Service
@RequiredArgsConstructor
public class UserSignService {

    private final UserRepository userRepository;
    private final UserFindService userFindService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long signUp(SignUpRequest signUpRequest) {
        checkDuplicatedEmail(signUpRequest.getEmail());
        User user = User.create(signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()),
                signUpRequest.getName(),
                signUpRequest.getPhoneNumber(),
                signUpRequest.getIntroduction(),
                toUserPositionSet(signUpRequest.getUserPositionDtoList()),
                toSkillSet(signUpRequest.getSkillDtoList()),
                toAddress(signUpRequest.getAddressDto()),
                AuthProviderType.local);
        userRepository.save(user);
        return user.getId();
    }

    @Transactional(readOnly = true)
    public SignInResponse signIn(SignInRequest signInRequest) {
        User user = userFindService.findByEmail(signInRequest.getEmail());
        checkMatchedPassword(signInRequest.getPassword(), user.getPassword());
        return new SignInResponse(user.getId());
    }

    private void checkMatchedPassword(String requestPassword, String userPassword) {
        if (!passwordEncoder.matches(requestPassword, userPassword)) {
            throw new PasswordNotMatchedException("Password is not matched!");
        }
    }

    private void checkDuplicatedEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailDuplicateException(String.format("%s is duplicated email", email));
        }
    }
}
