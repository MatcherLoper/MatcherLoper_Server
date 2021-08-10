package com.toy.matcherloper.web.bind;

import lombok.Getter;
import lombok.ToString;

import java.beans.ConstructorProperties;

@Getter
@ToString
public class ApiResult<T> {
    
    private final T data;
    private final String message;

    @ConstructorProperties({"data", "message"})
    public ApiResult(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResult<T> succeed(T data) {
        return new ApiResult<>(data, null);
    }

    public static <T> ApiResult<T> failed(Throwable throwable) {
        return new ApiResult<>(null, throwable.getMessage());
    }

    public static <T> ApiResult<T> failed(String message) {
        return new ApiResult<>(null, message);
    }
}
