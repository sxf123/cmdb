package com.ops.commons.exception;

import com.ops.commons.enums.ErrorCodeAndMsg;

public class BaseException extends RuntimeException {
    private static final long serialVersionUID = -6370612186038915645L;

    private final ErrorCodeAndMsg response;

    public BaseException(ErrorCodeAndMsg response) {
        this.response = response;
    }

    public ErrorCodeAndMsg getResponse() {
        return response;
    }
}
