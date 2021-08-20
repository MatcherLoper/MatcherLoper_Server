package com.toy.matcherloper.web.user.api.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInResponse {

    private Long id;
    private String token;

    public SignInResponse(Long id) {
        this.id = id;
    }
    public SignInResponse(Long id, String token) {
        this.id = id;
        this.token = token;
    }
}
