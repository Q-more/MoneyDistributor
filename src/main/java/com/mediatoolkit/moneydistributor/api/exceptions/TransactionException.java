package com.mediatoolkit.moneydistributor.api.exceptions;

import com.mediatoolkit.moneydistributor.api.exceptions.enums.ApiErrorCode;

public class TransactionException extends RuntimeException {
    private ApiErrorCode code;

    public TransactionException(ApiErrorCode code) {
        super(code.getCode());
        this.code = code;
    }

    public TransactionException(ApiErrorCode code, Throwable throwable) {
        super(code.getMessage(), throwable);
        this.code = code;
    }

    public TransactionException(ApiErrorCode code, String message, Throwable throwable) {
        super(code.getCode() + " " + message, throwable);
        this.code = code;
    }

    public TransactionException(ApiErrorCode code, String message) {
        super(code.getMessage() + " " + message);
    }

    public ApiErrorCode getCode() {
        return code;
    }
}
