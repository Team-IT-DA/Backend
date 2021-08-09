package com.itda.apiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiResult<T> {

    private boolean success;
    private T data;
    private ErrorCode error;

    public static <T> ApiResult<T> ok(T data) {
        return new ApiResult<>(true, data, null);
    }
}
