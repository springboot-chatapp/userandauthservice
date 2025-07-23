package com.chatapp.usermanagement.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class BaseResponse<T> {
    private int code;
    public String time;
    private String correlationId;
    private String method;
    private T data;
    private String message;
    private List<ErrorResponse> errors;
    private String path;
}