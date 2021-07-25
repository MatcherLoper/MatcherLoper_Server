package com.toy.matcherloper.web.user.api.dto.request;

import com.toy.matcherloper.web.user.api.dto.AddressDto;
import com.toy.matcherloper.web.user.api.dto.PositionDto;
import com.toy.matcherloper.web.user.api.dto.SkillDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor
public class UserUpdateRequest {

    @Email(message = "잘못된 형식의 이메일입니다.")
    private String email;

    @NotBlank(message = "비밀번호가 공백이면 안됩니다.")
    private String password;

    @NotEmpty(message = "이름이 비어있으면 안됩니다.")
    private String name;

    @NotEmpty(message = "핸드폰 번호를 입력해주십시오.")
    private String phoneNumber;

    private String introduction;

    private List<PositionDto> userPositionList;

    private List<SkillDto> skillList;

    private AddressDto address;

}
