package com.toy.matcherloper.web.entry.api.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EntryResponse {

    private EntryTotalDataResponse entryTotalDataResponse;
    private EntryNoneUserResponse entryNoneUserResponse;
    private EntryMatchingUserResponse entryMatchingUserResponse;

    public EntryResponse(EntryTotalDataResponse entryTotalDataResponse,
                         EntryNoneUserResponse entryNoneUserResponse,
                         EntryMatchingUserResponse entryMatchingUserResponse) {
        this.entryTotalDataResponse = entryTotalDataResponse;
        this.entryNoneUserResponse = entryNoneUserResponse;
        this.entryMatchingUserResponse = entryMatchingUserResponse;
    }
}
