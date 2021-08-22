package com.toy.matcherloper.web.user.api.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInResponse {

    private Long id;
    private String authenticationToken;

    public SignInResponse(Long id, String authenticationToken) {
        this.id = id;
        this.authenticationToken = authenticationToken;
    }
}
