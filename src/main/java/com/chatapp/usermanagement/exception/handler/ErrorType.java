package com.chatapp.usermanagement.exception.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND,"User not found"),
    INVALID_REQUEST(HttpStatus.UNPROCESSABLE_ENTITY.value(),HttpStatus.UNPROCESSABLE_ENTITY, "Invalid request data"),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, "User already exists"),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, "Database operation failed"),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, "Unauthorized access"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"),
    MANDATORY_FIELD_MISSING(HttpStatus.UNPROCESSABLE_ENTITY.value(), HttpStatus.UNPROCESSABLE_ENTITY, "Mandatory field Missing"),
    MESSAGE_NOT_READABLE(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "Message not readable"),
    MISSING_REQUEST_HEADER(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "Missing request header"),
    USER_PROFILE_CREATED(HttpStatus.CREATED.value(), HttpStatus.CREATED, "User profile created successfully"),
    GET_USER_PROFILE_SUCCESS(HttpStatus.OK.value(), HttpStatus.OK, "User profile retrieved successfully"),
    NO_DATA_FOUND(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, "No data found"),
    USER_PROFILE_UPDATED(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED, "User profile updated successfully");


    private final int code;
    private final HttpStatus httpStatus;
    private final String message;

    ErrorType(int code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}