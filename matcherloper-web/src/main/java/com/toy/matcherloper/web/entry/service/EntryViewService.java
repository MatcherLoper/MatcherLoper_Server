package com.toy.matcherloper.web.entry.service;

import com.toy.matcherloper.core.user.repository.UserRepository;
import com.toy.matcherloper.web.entry.api.dto.response.EntryResponse;
import com.toy.matcherloper.web.entry.repository.UserNoneCountQueryRepository;
import com.toy.matcherloper.web.room.repository.RoomFindQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EntryViewService {

    private final RoomFindQueryRepository roomFindQueryRepository;
    private final UserRepository userRepository;
    private final UserNoneCountQueryRepository userNoneCountQueryRepository;

    @Transactional(readOnly = true)
    public EntryResponse getOpenedRoomCnt() {
        int openedRoomCnt = roomFindQueryRepository.findAllByOpenWithUser().size();
        int allUserCnt = userRepository.findAll().size();
        int backEndCnt = userNoneCountQueryRepository.getBackEndUserCnt();
        int frontEndUserCnt = userNoneCountQueryRepository.getFrontEndUserCnt();
        int androidUserCnt = userNoneCountQueryRepository.getAndroidUserCnt();
        int iosUserCnt = userNoneCountQueryRepository.getIosUserCnt();
        return new EntryResponse(openedRoomCnt, allUserCnt, backEndCnt, frontEndUserCnt, androidUserCnt, iosUserCnt);
    }
}
