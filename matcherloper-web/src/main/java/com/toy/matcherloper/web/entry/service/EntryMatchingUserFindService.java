package com.toy.matcherloper.web.entry.service;

import com.toy.matcherloper.web.entry.api.dto.response.EntryMatchingUserResponse;
import com.toy.matcherloper.web.entry.repository.UserMatchingQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EntryMatchingUserFindService {

    private final UserMatchingQueryRepository userMatchingQueryRepository;

    @Transactional(readOnly = true)
    public EntryMatchingUserResponse getMatchingUserCnt() {
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
