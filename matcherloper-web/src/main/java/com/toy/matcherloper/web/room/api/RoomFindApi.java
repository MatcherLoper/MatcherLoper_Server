package com.toy.matcherloper.web.room.api;

import com.toy.matcherloper.core.room.model.Room;
import com.toy.matcherloper.web.bind.ApiResult;
import com.toy.matcherloper.web.room.api.dto.response.RoomFindResponse;
import com.toy.matcherloper.web.room.service.RoomFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
            log.error(e.getMessage());
            return ApiResult.failed(e.getMessage());
        }
    }

    @GetMapping
    public ApiResult<List<RoomFindResponse>> showAll() {
        try {
            return ApiResult.succeed(toRoomFindResponseList(roomFindService.findAllWithUser()));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.failed(e.getMessage());
        }
    }

    @GetMapping("/open")
    public ApiResult<List<RoomFindResponse>> showAllByOpen() {
        try {
            return ApiResult.succeed(toRoomFindResponseList(roomFindService.findAllByOpenWithUser()));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.failed(e.getMessage());
        }
    }

    private List<RoomFindResponse> toRoomFindResponseList(List<Room> roomList) {
        return roomList.stream()
                .map(RoomFindResponse::new)
                .collect(Collectors.toList());
    }
}
