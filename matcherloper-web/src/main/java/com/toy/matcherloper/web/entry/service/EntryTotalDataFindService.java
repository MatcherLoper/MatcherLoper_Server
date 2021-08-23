package com.toy.matcherloper.web.entry.service;

import com.toy.matcherloper.core.user.repository.UserRepository;
import com.toy.matcherloper.web.entry.api.dto.response.EntryTotalDataResponse;
import com.toy.matcherloper.web.room.repository.RoomFindQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EntryTotalDataFindService {

    private final RoomFindQueryRepository roomFindQueryRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public EntryTotalDataResponse getTotalData() {
        int openedRoomCnt = roomFindQueryRepository.findAllByOpenWithUser().size();
        int allUserCnt = userRepository.findAll().size();
        return new EntryTotalDataResponse(openedRoomCnt, allUserCnt);
    }
}
