package com.toy.matcherloper.web.user.api;

import com.toy.matcherloper.web.bind.ApiResult;
import com.toy.matcherloper.web.user.api.dto.response.UserFindResponse;
import com.toy.matcherloper.web.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserFindApi {

    private final UserFindService userFindService;

    @GetMapping("/{userId}")
    public ApiResult<UserFindResponse> findOne(@PathVariable("userId") Long userId) {
        try {
            return ApiResult.succeed(new UserFindResponse(userFindService.findById(userId)));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.failed(e.getMessage());
        }
    }
}
