package com.toy.matcherloper.web.entry.api.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EntryTotalDataResponse {

    private int openedRoomCnt;
    private int allUserCnt;

    public EntryTotalDataResponse(int openedRoomCnt, int allUserCnt) {
        this.openedRoomCnt = openedRoomCnt;
        this.allUserCnt = allUserCnt;
    }
}
