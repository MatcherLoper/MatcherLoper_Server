package com.toy.matcherloper.web.user.api.dto.response;

import com.toy.matcherloper.core.user.model.Skill;
import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.core.user.model.UserPosition;
import com.toy.matcherloper.web.user.api.dto.SkillDto;
import com.toy.matcherloper.web.user.api.dto.UserPositionDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class UserFindResponse {

    private Long userId;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String introduction;
    private List<UserPositionDto> userPositionDtoList;
    private List<SkillDto> skillDtoList;

    public UserFindResponse(User user) {
        userId = user.getId();
        email = user.getEmail();
        password = user.getPassword();
        name = user.getName();
        phoneNumber = user.getPhoneNumber();
        introduction = user.getIntroduction();
        userPositionDtoList = toDtoUserPositionList(user.getUserPositionList());
        skillDtoList = toDtoSkillList(user.getSkillList());
    }

    private List<UserPositionDto> toDtoUserPositionList(List<UserPosition> userPositionList) {
        return userPositionList.stream()
                .map(UserPositionDto::new)
                .collect(Collectors.toList());
    }

    private List<SkillDto> toDtoSkillList(List<Skill> skillList) {
        return skillList.stream()
                .map(SkillDto::new)
                .collect(Collectors.toList());
    }
}
