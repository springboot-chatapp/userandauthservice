package com.chatapp.usermanagement.mapper;

import com.chatapp.usermanagement.exception.handler.ErrorType;
import com.chatapp.usermanagement.model.dto.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseMapper {

    public static <T> ResponseEntity<BaseResponse<T>> getResponseEntity(final HttpServletRequest httpServletRequest,final T data, final ErrorType errorType) {

        BaseResponse<T> response = new BaseResponse<>();
        response.setData(data);
        response.setCode(errorType.getCode());
        response.setCorrelationId(httpServletRequest.getHeader("X-Correlation-ID"));
        response.setMethod(httpServletRequest.getMethod());
        response.setMessage(errorType.getMessage());
        response.setPath(httpServletRequest.getRequestURI());
        response.setMethod(httpServletRequest.getMethod());
        return ResponseEntity.status(errorType.getHttpStatus()).body(response);
    }

}