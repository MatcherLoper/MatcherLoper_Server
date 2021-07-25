package com.toy.matcherloper.web.user.service;

import com.toy.matcherloper.core.user.model.Address;
import com.toy.matcherloper.core.user.model.Skill;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.model.UserPosition;
import com.toy.matcherloper.core.user.repository.UserRepository;
import com.toy.matcherloper.web.user.api.dto.AddressDto;
import com.toy.matcherloper.web.user.api.dto.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserSignService {

    private final UserRepository userRepository;

    @Transactional
    public Long signUp(SignUpRequest request) {
        checkDuplicatedEmail(request.getEmail());

        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .introduction(request.getIntroduction())
                .userPositionList(toUserPosition(request))
                .skillList(toSkill(request))
                .address(toAddress(request))
                .build();

        userRepository.save(user);

        return user.getId();
    }


    private void checkDuplicatedEmail(String email) {
        if (userRepository.findUserByEmail(email).isPresent()) {
            throw new IllegalArgumentException(String.format("%s is duplicated email", email));
        }
    }

    private List<UserPosition> toUserPosition(SignUpRequest request) {
        return request.getUserPositionList().stream()
                .map(p -> new UserPosition(p.getType()))
                .collect(Collectors.toList());
    }

    private List<Skill> toSkill(SignUpRequest request) {
        return request.getSkillList().stream()
                .map(s -> new Skill(s.getName()))
                .collect(Collectors.toList());
    }

    private Address toAddress(SignUpRequest request) {
        AddressDto addressDto = request.getAddress();

        return new Address(addressDto.getCity(), addressDto.getDetailed());
    }
}
