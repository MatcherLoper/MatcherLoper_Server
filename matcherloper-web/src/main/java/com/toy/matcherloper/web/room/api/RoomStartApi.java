package com.toy.matcherloper.web.room.api;

import com.toy.matcherloper.web.bind.ApiResult;
import com.toy.matcherloper.web.room.service.RoomStartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RoomStartApi {

    private final RoomStartService roomStartService;

    @PutMapping("/api/v1/rooms/{roomId}/start")
    public ApiResult<Long> start(@PathVariable Long roomId) {
        try {
            return ApiResult.succeed(roomStartService.start(roomId));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.failed(e.getMessage());
        }
    }
}
