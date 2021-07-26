package com.toy.matcherloper.web.user.service;

import com.toy.matcherloper.core.user.model.Address;
import com.toy.matcherloper.core.user.model.Skill;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.model.UserPosition;
import com.toy.matcherloper.core.user.repository.UserRepository;
import com.toy.matcherloper.web.user.api.dto.AddressDto;
import com.toy.matcherloper.web.user.api.dto.request.SignInRequest;
import com.toy.matcherloper.web.user.api.dto.request.SignUpRequest;
import com.toy.matcherloper.web.user.api.dto.response.SignInResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserSignService {

    private final UserRepository userRepository;
    private final UserFindService userFindService;

    @Transactional
    public Long signUp(SignUpRequest signUpRequest) {
        checkDuplicatedEmail(signUpRequest.getEmail());

        User user = User.builder()
                .email(signUpRequest.getEmail())
                .password(signUpRequest.getPassword())
                .name(signUpRequest.getName())
                .phoneNumber(signUpRequest.getPhoneNumber())
                .introduction(signUpRequest.getIntroduction())
                .userPositionList(requestToUserPositionList(signUpRequest))
                .skillList(requestToSkillList(signUpRequest))
                .address(toAddress(signUpRequest))
                .build();

        userRepository.save(user);

        return user.getId();
    }

    @Transactional(readOnly = true)
    public SignInResponse signIn(SignInRequest signInRequest) {
        User user = userFindService.findUserByEmail(signInRequest.getEmail());
        checkMatchedPassword(signInRequest.getPassword(), user.getPassword());

        return new SignInResponse(user.getId());
    }

    private void checkDuplicatedEmail(String email) {
        if (userRepository.findUserByEmail(email).isPresent()) {
            throw new IllegalArgumentException(String.format("%s is duplicated email", email));
        }
    }

    /**
     * 추후, Spring Security -> PasswordEncoder 로 매치 확인
     */
    private void checkMatchedPassword(String requestPassword, String userPassword) {
        if (!requestPassword.equals(userPassword)) {
            throw new IllegalArgumentException("Password is not matched");
        }
    }

    private List<UserPosition> requestToUserPositionList(SignUpRequest request) {
        return request.getUserPositionList().stream()
                .map(p -> new UserPosition(p.getType()))
                .collect(Collectors.toList());

    }

    private List<Skill> requestToSkillList(SignUpRequest request) {
        return request.getSkillList().stream()
                .map(s -> new Skill(s.getName()))
                .collect(Collectors.toList());
    }

    private Address toAddress(SignUpRequest request) {
        AddressDto addressDto = request.getAddress();

        return new Address(addressDto.getCity(), addressDto.getDetailed());
    }
}
