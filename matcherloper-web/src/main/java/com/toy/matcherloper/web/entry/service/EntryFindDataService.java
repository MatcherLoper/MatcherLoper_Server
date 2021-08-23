package com.toy.matcherloper.web.entry.service;

import com.toy.matcherloper.core.user.repository.UserRepository;
import com.toy.matcherloper.web.entry.api.dto.response.EntryMatchingUserResponse;
import com.toy.matcherloper.web.entry.api.dto.response.EntryNoneUserResponse;
import com.toy.matcherloper.web.entry.api.dto.response.EntryResponse;
import com.toy.matcherloper.web.entry.api.dto.response.EntryTotalDataResponse;
import com.toy.matcherloper.web.entry.repository.UserMatchingQueryRepository;
import com.toy.matcherloper.web.entry.repository.UserNoneCountQueryRepository;
import com.toy.matcherloper.web.room.repository.RoomFindQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EntryFindDataService {

    private final UserRepository userRepository;
    private final RoomFindQueryRepository roomFindQueryRepository;
    private final UserNoneCountQueryRepository userNoneCountQueryRepository;
    private final UserMatchingQueryRepository userMatchingQueryRepository;

    public EntryResponse findEntryData() {
        return new EntryResponse(findEntryTotalData(),
                findNoneUserData(),
                findMatchingUserData());
    }

    private EntryTotalDataResponse findEntryTotalData() {
        int openedRoomCnt = roomFindQueryRepository.findAllByOpenWithUser().size();
        int allUserCnt = userRepository.findAll().size();
        return new EntryTotalDataResponse(openedRoomCnt, allUserCnt);
    }

    private EntryNoneUserResponse findNoneUserData() {
        int backEndUserCnt = userNoneCountQueryRepository.getBackEndUserCnt();
        int frontEndUserCnt = userNoneCountQueryRepository.getFrontEndUserCnt();
        int androidUserCnt = userNoneCountQueryRepository.getAndroidUserCnt();
        int iosUserCnt = userNoneCountQueryRepository.getIosUserCnt();
        return new EntryNoneUserResponse(backEndUserCnt, frontEndUserCnt, androidUserCnt, iosUserCnt);
    }

    private EntryMatchingUserResponse findMatchingUserData() {
        int backEndMatchingUserCnt = userMatchingQueryRepository.getMatchingBackEndUserCnt();
        int frontEndMatchingUserCnt = userMatchingQueryRepository.getMatchingFrontEndUserCnt();
        int androidMatchingUserCnt = userMatchingQueryRepository.getMatchingAndroidUserCnt();
        int iosMatchingUserCnt = userMatchingQueryRepository.getMatchingIosUserCnt();
        return new EntryMatchingUserResponse(backEndMatchingUserCnt,
                frontEndMatchingUserCnt,
                androidMatchingUserCnt,
                iosMatchingUserCnt);
    }
}
