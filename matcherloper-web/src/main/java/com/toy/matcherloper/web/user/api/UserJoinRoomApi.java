package com.toy.matcherloper.web.user.api;

import com.toy.matcherloper.web.bind.ApiResult;
import com.toy.matcherloper.web.user.api.dto.request.UserJoinRoomRequest;
import com.toy.matcherloper.web.user.service.UserJoinRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserJoinRoomApi {

    private final UserJoinRoomService userJoinRoomService;

    @PostMapping("/{userId}/joining/room")
    public ApiResult<Long> join(@PathVariable Long userId,
                                @RequestBody UserJoinRoomRequest request) {
        try {
            final Long userRoomId = userJoinRoomService.join(userId, request.getPosition());
            return ApiResult.succeed(userRoomId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.failed(e.getMessage());
        }
    }

}
