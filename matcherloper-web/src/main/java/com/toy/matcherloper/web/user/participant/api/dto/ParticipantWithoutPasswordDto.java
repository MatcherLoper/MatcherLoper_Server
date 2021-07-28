package com.toy.matcherloper.web.user.participant.api.dto;

import com.toy.matcherloper.core.user.model.Participant;
import com.toy.matcherloper.core.user.model.Skill;
import com.toy.matcherloper.core.user.model.UserPosition;
import com.toy.matcherloper.web.user.api.dto.AddressDto;
import com.toy.matcherloper.web.user.api.dto.SkillDto;
import com.toy.matcherloper.web.user.api.dto.UserPositionDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ParticipantWithoutPasswordDto {

    private String name;
    private String email;
    private String phoneNumber;
    private String introduction;
    private List<UserPositionDto> userPositionList;
    private List<SkillDto> skillList;
    private AddressDto address;

    public ParticipantWithoutPasswordDto(Participant participant) {
        this.name = participant.getName();
        this.email = participant.getEmail();
        this.phoneNumber = participant.getPhoneNumber();
        this.introduction = participant.getIntroduction();
        this.userPositionList = toUserPositionList(participant.getUserPositionList());
        this.skillList = toSkillList(participant.getSkillList());
        this.address = new AddressDto(participant.getAddress());
    }

    private List<UserPositionDto> toUserPositionList(List<UserPosition> userPositionList) {
        return userPositionList.stream()
                .map(UserPositionDto::new)
                .collect(Collectors.toList());
    }

    private List<SkillDto> toSkillList(List<Skill> skillList) {
        return skillList.stream()
                .map(SkillDto::new)
                .collect(Collectors.toList());
    }
}
