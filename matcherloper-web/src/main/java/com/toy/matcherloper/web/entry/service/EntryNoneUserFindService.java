package com.toy.matcherloper.web.entry.service;

import com.toy.matcherloper.web.entry.api.dto.response.EntryNoneUserResponse;
import com.toy.matcherloper.web.entry.repository.UserNoneCountQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EntryNoneUserFindService {

    private final UserNoneCountQueryRepository userNoneCountQueryRepository;

    @Transactional(readOnly = true)
    public EntryNoneUserResponse getNoneUserCnt() {
        int backEndUserCnt = userNoneCountQueryRepository.getBackEndUserCnt();
        int frontEndUserCnt = userNoneCountQueryRepository.getFrontEndUserCnt();
        int androidUserCnt = userNoneCountQueryRepository.getAndroidUserCnt();
        int iosUserCnt = userNoneCountQueryRepository.getIosUserCnt();
        return new EntryNoneUserResponse(backEndUserCnt, frontEndUserCnt, androidUserCnt, iosUserCnt);
    }
}
