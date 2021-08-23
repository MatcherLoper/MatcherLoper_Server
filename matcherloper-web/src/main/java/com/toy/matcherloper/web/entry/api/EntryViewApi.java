package com.toy.matcherloper.web.entry.api;

import com.toy.matcherloper.web.bind.ApiResult;
import com.toy.matcherloper.web.entry.api.dto.response.EntryMatchingUserResponse;
import com.toy.matcherloper.web.entry.api.dto.response.EntryNoneUserResponse;
import com.toy.matcherloper.web.entry.api.dto.response.EntryTotalDataResponse;
import com.toy.matcherloper.web.entry.service.EntryMatchingUserFindService;
import com.toy.matcherloper.web.entry.service.EntryNoneUserFindService;
import com.toy.matcherloper.web.entry.service.EntryTotalDataFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/entry")
@RequiredArgsConstructor
public class EntryViewApi {

    private final EntryTotalDataFindService entryTotalDataFindService;
    private final EntryNoneUserFindService entryNoneUserFindService;
    private final EntryMatchingUserFindService entryMatchingUserFindService;

    @GetMapping("/total")
    public ApiResult<EntryTotalDataResponse> getTotalData() {
        try {
            return ApiResult.succeed(entryTotalDataFindService.getTotalData());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.failed(e.getMessage());
        }
    }

    @GetMapping("/none")
    public ApiResult<EntryNoneUserResponse> getNoneUserCnt() {
        try {
            return ApiResult.succeed(entryNoneUserFindService.getNoneUserCnt());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.failed(e.getMessage());
        }
    }

    @GetMapping("/matching")
    public ApiResult<EntryMatchingUserResponse> getMatchingUserCnt() {
        try {
            return ApiResult.succeed(entryMatchingUserFindService.getMatchingUserCnt());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.failed(e.getMessage());
        }
    }
}
