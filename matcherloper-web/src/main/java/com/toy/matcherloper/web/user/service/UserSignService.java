package com.toy.matcherloper.web.user.service;

import com.toy.matcherloper.core.user.exception.PasswordNotMatchedException;
import com.toy.matcherloper.core.user.model.Address;
import com.toy.matcherloper.core.user.model.Skill;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.model.UserPosition;
import com.toy.matcherloper.core.user.model.type.RoleType;
import com.toy.matcherloper.core.user.repository.UserRepository;
import com.toy.matcherloper.web.user.api.dto.AddressDto;
import com.toy.matcherloper.web.user.api.dto.SkillDto;
import com.toy.matcherloper.web.user.api.dto.UserPositionDto;
import com.toy.matcherloper.web.user.api.dto.request.SignInRequest;
import com.toy.matcherloper.web.user.api.dto.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
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
                .roleType(RoleType.NONE)
                .userPositionSet(toUserPositionSet(signUpRequest.getUserPositionDtoList()))
                .skillSet(toSkillSet(signUpRequest.getSkillDtoList()))
                .address(toAddress(signUpRequest.getAddressDto()))
                .build();
        userRepository.save(user);
        return user.getId();
    }

    @Transactional(readOnly = true)
    public Long signIn(SignInRequest signInRequest) {
        User user = userFindService.findByEmail(signInRequest.getEmail());
        if (user.isMatchingPassword(signInRequest.getPassword())) {
            throw new PasswordNotMatchedException("Password is not matched!");
        }
        return user.getId();
    }

    private void checkDuplicatedEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException(String.format("%s is duplicated email", email));
        }
    }

    private Set<UserPosition> toUserPositionSet(List<UserPositionDto> userPositions) {
        return userPositions.stream()
                .map(p -> new UserPosition(p.getType()))
                .collect(Collectors.toSet());
    }

    private Set<Skill> toSkillSet(List<SkillDto> skills) {
        return skills.stream()
                .map(s -> new Skill(s.getName()))
                .collect(Collectors.toSet());
    }

    private Address toAddress(AddressDto addressDto) {
        return new Address(addressDto.getCity(), addressDto.getDetailed());
    }
}
