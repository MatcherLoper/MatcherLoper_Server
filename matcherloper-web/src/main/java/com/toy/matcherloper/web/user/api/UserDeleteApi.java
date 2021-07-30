package com.toy.matcherloper.web.user.api;

import com.toy.matcherloper.web.bind.ApiResult;
import com.toy.matcherloper.web.user.service.UserDeleteService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserDeleteApi {

    private final UserDeleteService userDeleteService;

    @ApiOperation(value = "회원 탈퇴", notes = "User의 id와 일치하는 회원 삭제")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 탈퇴 성공"),
            @ApiResponse(code = 400, message = "회원 탈퇴 실패")
    })
    @DeleteMapping("/{userId}")
    public ApiResult<Long> delete(@PathVariable Long userId) {
        try {
            Long deletedUserId = userDeleteService.delete(userId);
            return ApiResult.succeed(deletedUserId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.failed("회원 탈퇴 실패");
        }
    }
}
