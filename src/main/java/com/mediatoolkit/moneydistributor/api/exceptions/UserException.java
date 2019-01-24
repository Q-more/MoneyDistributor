package com.mediatoolkit.moneydistributor.api.exceptions;

import com.mediatoolkit.moneydistributor.api.exceptions.enums.ApiErrorCode;

public class UserException extends RuntimeException {
    private ApiErrorCode code;

    public UserException(ApiErrorCode errorCode) {
        super(errorCode.getMessage());
        code = errorCode;
    }

    public UserException(ApiErrorCode errorCode, Throwable throwable) {
        super(errorCode.getMessage(), throwable);
        code = errorCode;
    }

    public UserException(ApiErrorCode errorCode, String message) {
        super(errorCode.getCode() + " " + message);
        code = errorCode;
    }

    public ApiErrorCode getCode() {
        return code;
    }
}
