package com.toy.matcherloper.web.entry.api.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EntryNoneUserResponse {

    private int backEndNoneUserCnt;
    private int frontEndUserCnt;
    private int androidUserCnt;
    private int iosUserCnt;

    public EntryNoneUserResponse(int backEndUserCnt, int frontEndUserCnt, int androidUserCnt, int iosUserCnt) {
        this.backEndNoneUserCnt = backEndUserCnt;
        this.frontEndUserCnt = frontEndUserCnt;
        this.androidUserCnt = androidUserCnt;
        this.iosUserCnt = iosUserCnt;
    }
}
