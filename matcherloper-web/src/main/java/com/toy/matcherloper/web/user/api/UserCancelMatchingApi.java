package com.toy.matcherloper.web.user.api;

import com.toy.matcherloper.web.bind.ApiResult;
import com.toy.matcherloper.web.user.service.UserCancelMatchingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserCancelMatchingApi {

    private final UserCancelMatchingService userCancelMatchingService;

    @PutMapping("/{userId}/cancel/matching")
    public ApiResult<Long> cancelMatching(@PathVariable Long userId) {
        try {
            return ApiResult.succeed(userCancelMatchingService.cancelMatching(userId));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.failed(e.getMessage());
        }
    }
}
