package com.chatapp.usermanagement.exception.handler;

import com.chatapp.usermanagement.exception.UserManagementException;
import com.chatapp.usermanagement.model.dto.response.BaseResponse;
import com.chatapp.usermanagement.model.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<BaseResponse> handleMissingRequestHeaderException(MissingRequestHeaderException ex, final HttpServletRequest httpServletRequest) {
        return getResponseEntity(httpServletRequest, ErrorType.MISSING_REQUEST_HEADER, Optional.of(ex.getMessage()));
    }

// handler IllegalArgumentException to send a custom error response
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {

        return new ResponseEntity<>(ex.getMessage(), org.springframework.http.HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleSqlException(SQLException ex) {

        return new ResponseEntity<>(ex.getMessage(), org.springframework.http.HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        return new org.springframework.http.ResponseEntity<>("A null value was encountered: " + ex.getMessage(), org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, final HttpServletRequest httpServletRequest) {
        return getResponseEntity(httpServletRequest, ErrorType.MESSAGE_NOT_READABLE, Optional.ofNullable(ex.getMessage()));

    }

    @ExceptionHandler(UserManagementException.class)
    public ResponseEntity<BaseResponse<Object>> handleUserManagementException(UserManagementException ex, final HttpServletRequest httpServletRequest) {
        return createBaseResponse(ex, httpServletRequest);

    }

    private String sanitizeValue(String input){
        if(Objects.nonNull(input)){
            return input
                    .replace('\n','_')
                    .replace('\r', '_');
        }
        return input;
    }

    private <T> ResponseEntity<BaseResponse<T>> createBaseResponse(UserManagementException ex, final HttpServletRequest httpServletRequest) {
        BaseResponse<T> response = new BaseResponse<>();

        ErrorResponse errorResponse ;

        String errorMessage = ex.getErrorMessage();

        if(!StringUtils.hasText(errorMessage)) {
            errorResponse = new ErrorResponse(ex.getErrorType().getMessage(), String.valueOf(ex.getErrorType().getCode()));
        } else{
            errorResponse = new ErrorResponse(sanitizeValue(errorMessage), String.valueOf(ex.getErrorType().getCode()));
        }

//        response.setStatus(Integer.parseInt(String.valueOf(ex.getErrorType().getHttpStatus().value())));
        response.setCode(ex.getErrorType().getCode());
        response.setCorrelationId(httpServletRequest.getHeader("X-Correlation-ID"));
        response.setMethod(httpServletRequest.getMethod());
        response.setMessage(ex.getErrorType().getMessage());
        response.setPath(httpServletRequest.getRequestURI());
        List<ErrorResponse> errorList = new ArrayList<>();
        errorList.add(errorResponse);
        response.setErrors(errorList);
        return ResponseEntity.status(ex.getErrorType().getHttpStatus()).body(response);
    }

    private ResponseEntity<BaseResponse> getResponseEntity(final HttpServletRequest httpServletRequest, final ErrorType errorType, final Optional<String> errorMessage) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(String.valueOf(errorType.getCode()));
        errorResponse.setMessage(errorType.getMessage());
        errorMessage.ifPresent(errorResponse::setMessage);

        BaseResponse response = new BaseResponse();
//        response.setStatus(errorType.getHttpStatus().value());
        response.setCode(errorType.getCode());
        response.setCorrelationId(httpServletRequest.getHeader("X-Correlation-ID"));
        response.setMethod(httpServletRequest.getMethod());
        response.setMessage(errorType.getMessage());
        response.setPath(httpServletRequest.getRequestURI());
        return ResponseEntity.status(errorType.getHttpStatus()).body(response);
    }

}