package com.toy.matcherloper.web.user.service;

import com.toy.matcherloper.auth.jwt.JwtTokenProvider;
import com.toy.matcherloper.core.user.exception.PasswordNotMatchedException;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.model.type.AuthProviderType;
import com.toy.matcherloper.core.user.repository.UserPositionRepository;
import com.toy.matcherloper.core.user.repository.UserRepository;
import com.toy.matcherloper.web.user.api.dto.request.SignInRequest;
import com.toy.matcherloper.web.user.api.dto.request.SignUpRequest;
import com.toy.matcherloper.web.user.api.dto.response.SignInResponse;
import com.toy.matcherloper.web.user.exception.EmailDuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.toy.matcherloper.web.utils.DtoConverter.*;

@Service
@RequiredArgsConstructor
public class UserSignService {

    private final UserRepository userRepository;
    private final UserFindService userFindService;
    private final UserPositionRepository userPositionRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

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
        userPositionRepository.saveAll(user.getUserPositionSet());
        userRepository.save(user);
        return user.getId();
    }

    @Transactional
    public SignInResponse signIn(SignInRequest signInRequest) {
        User user = userFindService.findByEmail(signInRequest.getEmail());
        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw new PasswordNotMatchedException("Password is not matched!");
        }
        user.changeDeviceToken(signInRequest.getDeviceToken());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getEmail(),
                        signInRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.createToken(authentication);
        return new SignInResponse(user.getId(), token);
    }

    private void checkDuplicatedEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailDuplicateException(String.format("%s is duplicated email", email));
        }
    }
}
