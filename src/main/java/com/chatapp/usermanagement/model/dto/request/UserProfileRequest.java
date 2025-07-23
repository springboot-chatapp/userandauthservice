// UserProfileRequest.java
package com.chatapp.usermanagement.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserProfileRequest extends BaseRequestData{

    @JsonProperty("data")
    private UserProfileData data;

}