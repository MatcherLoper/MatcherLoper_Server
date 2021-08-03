package com.toy.matcherloper.web.user.api.dto.response;

import com.toy.matcherloper.core.user.model.Participant;
import com.toy.matcherloper.core.user.model.Skill;
import com.toy.matcherloper.core.user.model.UserPosition;
import com.toy.matcherloper.web.user.api.dto.SkillDto;
import com.toy.matcherloper.web.user.api.dto.UserPositionDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ParticipantFindResponse {

    private Long userId;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String introduction;
    private List<UserPositionDto> userPositions;
    private List<SkillDto> skills;

    public ParticipantFindResponse(Participant participant) {
        userId = participant.getId();
        email = participant.getEmail();
        password = participant.getPassword();
        name = participant.getName();
        phoneNumber = participant.getPhoneNumber();
        introduction = participant.getIntroduction();
        userPositions = toDtoUserPositionList(participant.getUserPositionSet());
        skills = toDtoSkillList(participant.getSkillSet());
    }

    private List<UserPositionDto> toDtoUserPositionList(Set<UserPosition> userPositionSet) {
        return userPositionSet.stream()
                .map(UserPositionDto::new)
                .collect(Collectors.toList());
    }

    private List<SkillDto> toDtoSkillList(Set<Skill> skillSet) {
        return skillSet.stream()
                .map(SkillDto::new)
                .collect(Collectors.toList());
    }
}
