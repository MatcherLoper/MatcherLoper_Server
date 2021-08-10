package com.toy.matcherloper.web.user.service;

import com.toy.matcherloper.core.user.exception.PasswordNotMatchedException;
import com.toy.matcherloper.core.user.model.Skill;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.repository.SkillRepository;
import com.toy.matcherloper.core.user.repository.UserPositionRepository;
import com.toy.matcherloper.core.user.repository.UserRepository;
import com.toy.matcherloper.web.user.api.dto.request.SignInRequest;
import com.toy.matcherloper.web.user.api.dto.request.SignUpRequest;
import com.toy.matcherloper.web.user.exception.EmailDuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.toy.matcherloper.web.utils.DtoConverter.*;

@Service
@RequiredArgsConstructor
public class UserSignService {

    private final UserRepository userRepository;
    private final UserFindService userFindService;
    private final SkillRepository skillRepository;
    private final UserPositionRepository userPositionRepository;

    @Transactional
    public Long signUp(SignUpRequest signUpRequest) {
        checkDuplicatedEmail(signUpRequest.getEmail());
        User user = User.create(signUpRequest.getEmail(),
                signUpRequest.getPassword(),
                signUpRequest.getName(),
                signUpRequest.getPhoneNumber(),
                signUpRequest.getIntroduction(),
                toUserPositionSet(signUpRequest.getUserPositionDtoList()),
                toSkillSet(signUpRequest.getSkillDtoList()),
                toAddress(signUpRequest.getAddressDto()));
        saveSkills(user.getSkillSet());
        userPositionRepository.saveAll(user.getUserPositionSet());
        userRepository.save(user);
        return user.getId();
    }

    @Transactional(readOnly = true)
    public Long signIn(SignInRequest signInRequest) {
        User user = userFindService.findByEmail(signInRequest.getEmail());
        if (user.isNotMatchingPassword(signInRequest.getPassword())) {
            throw new PasswordNotMatchedException("Password is not matched!");
        }
        return user.getId();
    }

    private void checkDuplicatedEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailDuplicateException(String.format("%s is duplicated email", email));
        }
    }

    private void saveSkills(Set<Skill> skillSet) {
        skillSet.stream()
                .filter(skill -> !skillRepository.findByName(skill.getName()).isPresent())
                .forEach(skillRepository::save);
    }
}
