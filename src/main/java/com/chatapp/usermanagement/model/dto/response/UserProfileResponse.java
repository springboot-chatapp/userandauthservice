package com.chatapp.usermanagement.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class UserProfileResponse {
    private String userId;
    private String firstName;
    private String lastName;
    private byte[] avatar;
    private String bio;
    @JsonProperty("isPrivate")
    private boolean isPrivate;
    private String statusMessage;
}