package com.toy.matcherloper.web.user.participant.api.dto;

import com.toy.matcherloper.core.user.model.Skill;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.model.UserPosition;
import com.toy.matcherloper.web.user.api.dto.AddressDto;
import com.toy.matcherloper.web.user.api.dto.SkillDto;
import com.toy.matcherloper.web.user.api.dto.UserPositionDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ParticipantWithoutPasswordDto {

    private String name;
    private String email;
    private String phoneNumber;
    private String introduction;
    private List<UserPositionDto> userPositions;
    private List<SkillDto> skills;
    private AddressDto address;

    public ParticipantWithoutPasswordDto(User participant) {
        this.name = participant.getName();
        this.email = participant.getEmail();
        this.phoneNumber = participant.getPhoneNumber();
        this.introduction = participant.getIntroduction();
        this.userPositions = toUserPositions(participant.getUserPositionSet());
        this.skills = toSkills(participant.getSkillSet());
        this.address = new AddressDto(participant.getAddress());
    }

    private List<UserPositionDto> toUserPositions(Set<UserPosition> userPositionSet) {
        return userPositionSet.stream()
                .map(UserPositionDto::new)
                .collect(Collectors.toList());
    }

    private List<SkillDto> toSkills(Set<Skill> skillSet) {
        return skillSet.stream()
                .map(SkillDto::new)
                .collect(Collectors.toList());
    }
}
