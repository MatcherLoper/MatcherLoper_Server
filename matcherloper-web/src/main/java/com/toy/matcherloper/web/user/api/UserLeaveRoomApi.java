package com.toy.matcherloper.web.user.api;

import com.toy.matcherloper.web.bind.ApiResult;
import com.toy.matcherloper.web.user.service.UserLeaveRoomService;
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
public class UserLeaveRoomApi {

    private final UserLeaveRoomService userLeaveRoomService;

    @PutMapping("/{userId}/leave/{roomId}")
    public ApiResult<Long> leaveRoom(@PathVariable Long userId,
                                     @PathVariable Long roomId) {
        try {
            return ApiResult.succeed(userLeaveRoomService.leave(userId, roomId));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.failed(e.getMessage());
        }
    }
}
