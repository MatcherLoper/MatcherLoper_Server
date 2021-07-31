package com.toy.matcherloper.web.user.api.dto.request;

import com.toy.matcherloper.web.user.api.dto.AddressDto;
import com.toy.matcherloper.web.user.api.dto.UserPositionDto;
import com.toy.matcherloper.web.user.api.dto.SkillDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class SignUpRequest {

    @Email(message = "잘못된 형식의 이메일입니다.")
    private String email;

    @NotBlank(message = "비밀번호가 공백이면 안됩니다.")
    private String password;

    @NotBlank(message = "이름이 비어있으면 안됩니다.")
    private String name;

    @NotEmpty(message = "핸드폰 번호를 입력해주십시오(010-1234-1234).")
    private String phoneNumber;

    @NotNull(message = "간단한 자기소개를 입력해주세요.")
    private String introduction;

    @NotNull(message = "개발 포지션을 추가해주세요.")
    private List<UserPositionDto> userPositionDtoList;

    @NotNull(message = "주 사용 스펙을 입력해주세요.")
    private List<SkillDto> skillDtoList;

    @NotNull(message = "주소를 입력해주세요.")
    private AddressDto addressDto;
}
