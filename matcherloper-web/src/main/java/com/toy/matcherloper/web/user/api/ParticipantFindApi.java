package com.toy.matcherloper.web.user.api;

import com.toy.matcherloper.core.user.model.Participant;
import com.toy.matcherloper.web.bind.ApiResult;
import com.toy.matcherloper.web.user.api.dto.response.ParticipantFindResponse;
import com.toy.matcherloper.web.user.service.ParticipantFindService;
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
public class ParticipantFindApi {

    private final ParticipantFindService participantFindService;

    @GetMapping("/{roomId}/participants")
    public ApiResult<List<ParticipantFindResponse>> findParticipants(@PathVariable("roomId") Long roomId) {
        try {
            List<Participant> participantListByRoom = participantFindService.findParticipantListByRoom(roomId);
            List<ParticipantFindResponse> participantFindResponses = toParticipantFindResponses(participantListByRoom);
            return ApiResult.succeed(participantFindResponses);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.failed(e.getMessage());
        }
    }

    private List<ParticipantFindResponse> toParticipantFindResponses(List<Participant> participantList) {
        return participantList.stream()
                .map(ParticipantFindResponse::new)
                .collect(Collectors.toList());
    }
}
