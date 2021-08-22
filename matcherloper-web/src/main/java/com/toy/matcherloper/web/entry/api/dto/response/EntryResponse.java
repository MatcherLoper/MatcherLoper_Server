package com.toy.matcherloper.web.entry.api.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EntryResponse {

    private int openedRoomCnt;
    private int allUserCnt;

    private int backEndNoneUserCnt;
    private int frontEndUserCnt;
    private int androidUserCnt;
    private int iosUserCnt;

    public EntryResponse(int openedRoomCnt, int allUserCnt, int backEndUserCnt, int frontEndUserCnt,
                         int androidUserCnt, int iosUserCnt) {
        this.openedRoomCnt = openedRoomCnt;
        this.allUserCnt = allUserCnt;
        this.backEndNoneUserCnt = backEndUserCnt;
        this.frontEndUserCnt = frontEndUserCnt;
        this.androidUserCnt = androidUserCnt;
        this.iosUserCnt = iosUserCnt;
    }
}
