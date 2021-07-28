package com.toy.matcherloper.web.user.owner.api.dto;

import com.toy.matcherloper.core.user.model.Owner;
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
public class OwnerWithoutPasswordDto {

    private String name;
    private String email;
    private String phoneNumber;
    private String introduction;
    private List<UserPositionDto> userPositionList;
    private List<SkillDto> skillList;
    private AddressDto address;


    public OwnerWithoutPasswordDto(Owner owner) {
        this.name = owner.getName();
        this.email = owner.getEmail();
        this.phoneNumber = owner.getPhoneNumber();
        this.introduction = owner.getIntroduction();
        this.userPositionList = toUserPositionList(owner.getUserPositionList());
        this.skillList = toSkillList(owner.getSkillList());
        this.address = new AddressDto(owner.getAddress());
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
