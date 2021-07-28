package com.toy.matcherloper.web.room.api;

import com.toy.matcherloper.web.bind.ApiResult;
import com.toy.matcherloper.web.room.api.dto.response.RoomFindResponse;
import com.toy.matcherloper.web.room.service.RoomFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomFindApi {

    private final RoomFindService roomFindService;

    @GetMapping("/{roomId}")
    public ApiResult<RoomFindResponse> showOne(@PathVariable Long roomId) {
        try {
            return ApiResult.succeed(new RoomFindResponse(roomFindService.findOne(roomId)));
        } catch (Exception e) {
            return ApiResult.failed(e.getMessage());
        }
    }
}
