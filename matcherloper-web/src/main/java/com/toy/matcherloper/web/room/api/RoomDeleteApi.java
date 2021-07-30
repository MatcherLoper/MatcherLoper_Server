package com.toy.matcherloper.web.room.api;

import com.toy.matcherloper.web.bind.ApiResult;
import com.toy.matcherloper.web.room.service.RoomDeleteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomDeleteApi {

    private final RoomDeleteService roomDeleteService;

    @DeleteMapping("/{roomId}")
    public ApiResult<Long> delete(@PathVariable Long roomId) {
        try {
            return ApiResult.succeed(roomDeleteService.delete(roomId));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.failed(e.getMessage());
        }
    }
}
