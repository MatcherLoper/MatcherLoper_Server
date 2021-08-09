package com.toy.matcherloper.web.user.api;

import com.toy.matcherloper.web.bind.ApiResult;
import com.toy.matcherloper.web.user.api.dto.request.UserUpdateRequest;
import com.toy.matcherloper.web.user.service.UserUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserUpdateApi {

    private final UserUpdateService userUpdateService;

    @PutMapping("/{userId}")
    public ApiResult<Long> update(@PathVariable("userId") Long userId,
                                  @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        try {
            Long updatedUserId = userUpdateService.update(userId, userUpdateRequest);
            return ApiResult.succeed(updatedUserId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.failed(e.getMessage());
        }
    }
}
