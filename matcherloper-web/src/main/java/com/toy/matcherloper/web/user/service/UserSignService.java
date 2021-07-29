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
                .userPositionList(ToUserPositionList(signUpRequest))
                .skillList(ToSkillList(signUpRequest))
                .address(toAddress(signUpRequest))
                .build();

        userRepository.save(user);

        return user.getId();
    }

    @Transactional(readOnly = true)
    public SignInResponse signIn(SignInRequest signInRequest) {
        User user = userFindService.findByEmail(signInRequest.getEmail());
        user.checkMatchedPassword(signInRequest.getPassword());

        return new SignInResponse(user.getId());
    }

    private void checkDuplicatedEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException(String.format("%s is duplicated email", email));
        }
    }

    private List<UserPosition> ToUserPositionList(SignUpRequest signUpRequest) {
        return signUpRequest.getUserPositionDtoList().stream()
                .map(p -> new UserPosition(p.getType()))
                .collect(Collectors.toList());

    }

    private List<Skill> ToSkillList(SignUpRequest signUpRequest) {
        return signUpRequest.getSkillDtoList().stream()
                .map(s -> new Skill(s.getName()))
                .collect(Collectors.toList());
    }

    private Address toAddress(SignUpRequest signUpRequest) {
        AddressDto addressDto = signUpRequest.getAddressDto();

        return new Address(addressDto.getCity(), addressDto.getDetailed());
    }
}
