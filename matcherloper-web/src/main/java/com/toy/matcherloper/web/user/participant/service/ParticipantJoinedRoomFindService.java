package com.toy.matcherloper.web.user.participant.service;

import com.toy.matcherloper.core.user.model.User;
import com.toy.matcherloper.web.user.participant.repository.ParticipantJoinedRoomFindQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParticipantJoinedRoomFindService {

    private final ParticipantJoinedRoomFindQueryRepository participantJoinedRoomFindQueryRepository;

    public List<User> findParticipantListByRoom(Long roomId) {
        return participantJoinedRoomFindQueryRepository.findParticipantListByRoom(roomId);
    }
}