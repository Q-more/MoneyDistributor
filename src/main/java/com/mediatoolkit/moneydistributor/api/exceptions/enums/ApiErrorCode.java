package com.mediatoolkit.moneydistributor.api.exceptions.enums;

/**
 * @author lucija
 */
public enum ApiErrorCode {
    EMPTY_FIELD(ApiErrorCode.EMPTY_FIELD_CODE, "Field is empty"),
    ALREADY_EXISTS(ApiErrorCode.ALREADY_EXISTS_CODE, "Already exists"),
    NOT_EXISTS(ApiErrorCode.NOT_EXISTS_CODE, "Not exists"),
    NULL_FIELD(ApiErrorCode.CAN_NOT_BE_NULL, "Can not be null"),
    INVALID_SIZE(ApiErrorCode.INVALID_SIZE_CODE, "Invalid field size"),
    EMAIL_ALREADY_EXISTS(ApiErrorCode.EMAIL_ALREADY_EXISTS_CODE, "Email already exists");

    public static final String EMPTY_FIELD_CODE = "error.empty";
    public static final String ALREADY_EXISTS_CODE = "error.alreadyExists";
    public static final String NOT_EXISTS_CODE = "error.notExists";
    public static final String CAN_NOT_BE_NULL = "error.null";
    public static final String INVALID_SIZE_CODE = "angular.error.size";
    public static final String EMAIL_ALREADY_EXISTS_CODE = "error.email.exists";

    private String code;
    private String message;

    ApiErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}