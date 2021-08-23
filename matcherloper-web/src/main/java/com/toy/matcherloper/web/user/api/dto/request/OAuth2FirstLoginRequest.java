package com.toy.matcherloper.web.user.api.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class OAuth2FirstLoginRequest {

    @Email(message = "잘못된 형식의 이메일입니다.")
    private String email;

    @NotBlank(message = "이름이 비어있으면 안됩니다.")
    private String name;

    @NotNull(message = "기기 토큰값은 Null이면 안됩니다.")
    private String deviceToken;

    public OAuth2FirstLoginRequest(String email, String name, String deviceToken) {
        this.email = email;
        this.name = name;
        this.deviceToken = deviceToken;
    }
}
