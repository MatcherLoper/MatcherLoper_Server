package com.toy.matcherloper.web.user.api.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInRequest {

    @Email(message = "잘못된 형식의 이메일 주소입니다.")
    private String email;

    @NotBlank(message = "비밀번호가 공백이면 안됩니다.")
    private String password;

    @NotNull(message = "기기 토큰값은 Null이면 안됩니다.")
    private String deviceToken;

    public SignInRequest(String email, String password, String deviceToken) {
        this.email = email;
        this.password = password;
        this.deviceToken = deviceToken;
    }
}
