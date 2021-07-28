package com.toy.matcherloper.web.room.api;

import com.toy.matcherloper.web.bind.ApiResult;
import com.toy.matcherloper.web.room.api.dto.request.CreateRoomRequest;
import com.toy.matcherloper.web.room.service.RoomCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomCreateApi {

    private final RoomCreateService roomCreateService;

    @PostMapping("/api/v1/room")
    public ApiResult<Long> createRoom(@RequestBody CreateRoomRequest request) {
        try {
            return ApiResult.succeed(roomCreateService.create(request));
        } catch (Exception e) {
            return ApiResult.failed(e.getMessage());
        }
    }
}
