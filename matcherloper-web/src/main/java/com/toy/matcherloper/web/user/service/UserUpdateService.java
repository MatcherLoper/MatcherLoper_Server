package com.toy.matcherloper.web.user.service;

import com.toy.matcherloper.core.user.model.Skill;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.repository.SkillRepository;
import com.toy.matcherloper.core.user.repository.UserPositionRepository;
import com.toy.matcherloper.web.user.api.dto.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.toy.matcherloper.web.utils.DtoConverter.*;

@Service
@RequiredArgsConstructor
public class UserUpdateService {

    private final UserFindService userFindService;
    private final SkillRepository skillRepository;
    private final UserPositionRepository userPositionRepository;

    @Transactional
    public Long update(Long userId, UserUpdateRequest userUpdateRequest) {
        User user = userFindService.findById(userId);
        user.update(userUpdateRequest.getEmail(),
                userUpdateRequest.getPassword(),
                userUpdateRequest.getName(),
                userUpdateRequest.getPhoneNumber(),
                userUpdateRequest.getIntroduction(),
                toUserPositionSet(userUpdateRequest.getUserPositionDtoList()),
                toSkillSet(userUpdateRequest.getSkillDtoList()),
                toAddress(userUpdateRequest.getAddressDto()));
        saveSkills(user.getSkillSet());
        userPositionRepository.saveAll(user.getUserPositionSet());
        return user.getId();
    }

    private void saveSkills(Set<Skill> skillSet) {
        skillSet.stream()
                .filter(skill -> !skillRepository.findByName(skill.getName()).isPresent())
                .forEach(skillRepository::save);
    }
}
