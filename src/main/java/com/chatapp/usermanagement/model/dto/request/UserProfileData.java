package com.chatapp.usermanagement.model.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileData {
    private String userId;
    private String firstName;
    private String lastName;
    private byte[] avatar;
    private String bio;
    private Boolean isPrivate;
    private String statusMessage;
}