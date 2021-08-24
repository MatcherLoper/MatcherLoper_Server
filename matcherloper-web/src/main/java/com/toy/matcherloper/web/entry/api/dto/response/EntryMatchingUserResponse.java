package com.toy.matcherloper.web.entry.api.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EntryMatchingUserResponse {

    private int backEndMatchingUserCnt;
    private int frontEndMatchingUserCnt;
    private int androidMatchingUserCnt;
    private int iosMatchingUserCnt;

    public EntryMatchingUserResponse(int backEndMatchingUserCnt, int frontEndMatchingUserCnt, int androidMatchingUserCnt, int iosMatchingUserCnt) {
        this.backEndMatchingUserCnt = backEndMatchingUserCnt;
        this.frontEndMatchingUserCnt = frontEndMatchingUserCnt;
        this.androidMatchingUserCnt = androidMatchingUserCnt;
        this.iosMatchingUserCnt = iosMatchingUserCnt;
    }
}
