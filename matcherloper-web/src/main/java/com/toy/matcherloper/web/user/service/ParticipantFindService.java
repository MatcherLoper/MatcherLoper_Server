package com.toy.matcherloper.web.user.service;

import com.toy.matcherloper.core.user.model.Participant;
import com.toy.matcherloper.web.user.repository.ParticipantFindQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParticipantFindService {

    private final ParticipantFindQueryRepository participantFindQueryRepository;

    public List<Participant> findParticipantListByRoom(Long roomId) {
        return participantFindQueryRepository.findParticipantListByRoom(roomId);
    }
}