package com.toy.matcherloper.web.user.api.dto;

import com.toy.matcherloper.core.user.model.Skill;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SkillDto {

    private String name;

    public SkillDto(String name) {
        this.name = name;
    }

    public SkillDto(Skill skill) {
        this.name = skill.getName();
    }
}
