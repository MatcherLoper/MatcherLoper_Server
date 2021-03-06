package com.toy.matcherloper.web.user.api;

import com.toy.matcherloper.web.bind.ApiResult;
import com.toy.matcherloper.web.user.api.dto.request.SignInRequest;
import com.toy.matcherloper.web.user.api.dto.request.SignUpRequest;
import com.toy.matcherloper.web.user.api.dto.response.SignInResponse;
import com.toy.matcherloper.web.user.service.UserSignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserSignApi {

    private final UserSignService userSignService;

    @PostMapping("/signup")
    public ApiResult<Long> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        try {
            Long signUpUserId = userSignService.signUp(signUpRequest);
            return ApiResult.succeed(signUpUserId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.failed(e.getMessage());
        }
    }

    @PostMapping("/signin")
    public ApiResult<SignInResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        try {
            return ApiResult.succeed(userSignService.signIn(signInRequest));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.failed(e.getMessage());
        }
    }
}
