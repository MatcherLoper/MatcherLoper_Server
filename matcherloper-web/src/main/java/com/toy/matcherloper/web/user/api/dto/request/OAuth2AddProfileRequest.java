package com.toy.matcherloper.web.user.api.dto.request;

import com.toy.matcherloper.web.user.api.dto.AddressDto;
import com.toy.matcherloper.web.user.api.dto.SkillDto;
import com.toy.matcherloper.web.user.api.dto.UserPositionDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class OAuth2AddProfileRequest {

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

    public OAuth2AddProfileRequest(String phoneNumber, String introduction, List<UserPositionDto> userPositionDtoList, List<SkillDto> skillDtoList, AddressDto addressDto) {
        this.phoneNumber = phoneNumber;
        this.introduction = introduction;
        this.userPositionDtoList = userPositionDtoList;
        this.skillDtoList = skillDtoList;
        this.addressDto = addressDto;
    }
}
