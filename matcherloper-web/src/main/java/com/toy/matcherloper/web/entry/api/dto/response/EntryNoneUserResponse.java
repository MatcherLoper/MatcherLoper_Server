package com.toy.matcherloper.web.entry.api.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EntryNoneUserResponse {

    private int backEndNoneUserCnt;
    private int frontEndNoneUserCnt;
    private int androidNoneUserCnt;
    private int iosNoneUserCnt;

    public EntryNoneUserResponse(int backEndUserCnt, int frontEndUserCnt, int androidUserCnt, int iosUserCnt) {
        this.backEndNoneUserCnt = backEndUserCnt;
        this.frontEndNoneUserCnt = frontEndUserCnt;
        this.androidNoneUserCnt = androidUserCnt;
        this.iosNoneUserCnt = iosUserCnt;
    }
}
