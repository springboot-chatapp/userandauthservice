package com.chatapp.usermanagement.exception;


import com.chatapp.usermanagement.exception.handler.ErrorType;
import lombok.Getter;

@Getter
public class UserManagementException extends RuntimeException{
    private final ErrorType errorType;
    private String errorMessage;

    public UserManagementException(ErrorType errorType) {
        this.errorType = errorType;
    }
    public UserManagementException(ErrorType errorType, String errorMessage) {
        this.errorType = errorType;
        this.errorMessage = errorMessage;
    }
}