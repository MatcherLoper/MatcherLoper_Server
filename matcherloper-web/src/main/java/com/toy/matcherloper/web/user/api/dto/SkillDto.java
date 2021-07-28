package com.toy.matcherloper.web.user.api.dto;

import com.toy.matcherloper.core.user.model.Skill;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class SkillDto {

    @NotEmpty(message = "스킬명을 적어주세요.")
    private String name;

    public SkillDto(Skill skill) {
        this.name = skill.getName();
    }
}