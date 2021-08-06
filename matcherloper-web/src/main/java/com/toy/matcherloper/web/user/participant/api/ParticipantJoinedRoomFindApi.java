package com.toy.matcherloper.web.user.participant.api;

import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.web.bind.ApiResult;
import com.toy.matcherloper.web.user.participant.api.dto.response.ParticipantJoinedRoomFindResponse;
import com.toy.matcherloper.web.user.participant.service.ParticipantJoinedRoomFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/rooms")
public class ParticipantJoinedRoomFindApi {

    private final ParticipantJoinedRoomFindService participantJoinedRoomFindService;

    @GetMapping("/{roomId}/users/type/participant")
    public ApiResult<List<ParticipantJoinedRoomFindResponse>> findParticipants(@PathVariable("roomId") Long roomId) {
        try {
            List<User> participantList = participantJoinedRoomFindService.findParticipantListByRoom(roomId);
            return ApiResult.succeed(toParticipantFindResponses(participantList));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.failed(e.getMessage());
        }
    }

    private List<ParticipantJoinedRoomFindResponse> toParticipantFindResponses(List<User> participantList) {
        return participantList.stream()
                .map(ParticipantJoinedRoomFindResponse::new)
                .collect(Collectors.toList());
    }
}
