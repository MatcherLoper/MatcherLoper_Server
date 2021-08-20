package com.toy.matcherloper.web.user.api;

import com.toy.matcherloper.web.bind.ApiResult;
import com.toy.matcherloper.web.user.api.dto.request.OAuth2AddProfileRequest;
import com.toy.matcherloper.web.user.api.dto.request.OAuth2FirstLoginRequest;
import com.toy.matcherloper.web.user.service.oauth.UserOAuth2AddProfileService;
import com.toy.matcherloper.web.user.service.oauth.UserOAuth2SaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserOAuth2SignApi {

    private final UserOAuth2SaveService userOAuth2SaveService;
    private final UserOAuth2AddProfileService userOAuth2AddProfileService;

    @PostMapping("/oauth2/signup")
    public ApiResult<Long> saveOAuth2User(@Valid @RequestBody OAuth2FirstLoginRequest oAuth2FirstLoginRequest) {
        try {
            return ApiResult.succeed(userOAuth2SaveService.saveOAuth2User(oAuth2FirstLoginRequest));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.failed(e.getMessage());
        }
    }

    @PostMapping("/oauth2/{email}/add")
    public ApiResult<Long> addProfileOAuth2User(@PathVariable("email") String userEmail,
                                             @Valid @RequestBody OAuth2AddProfileRequest oAuth2AddProfileRequest) {
        try {
            return ApiResult.succeed(userOAuth2AddProfileService.addProfileOAuth2User(userEmail, oAuth2AddProfileRequest));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.failed(e.getMessage());
        }
    }
}
