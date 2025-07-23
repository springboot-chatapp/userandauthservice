package com.chatapp.usermanagement.mapper;

import com.chatapp.usermanagement.model.dto.request.UserProfileRequest;
import com.chatapp.usermanagement.model.dto.response.UserProfileResponse;
import com.chatapp.usermanagement.model.entity.UserProfile;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class Mapper {

    // This method will convert UserProfileRequest to UserProfile entity
    // and return the UserProfile entity.
    public UserProfile toUserProfileEntity(UserProfileRequest userProfileRequest) {
        if (userProfileRequest == null) {
            return null;
        }

        return UserProfile.builder()
                .userId(userProfileRequest.getData().getUserId())
                .firstName(userProfileRequest.getData().getFirstName())
                .lastName(userProfileRequest.getData().getLastName())
                .avatar(userProfileRequest.getData().getAvatar())
                .isPrivate(userProfileRequest.getData().getIsPrivate() != null ? userProfileRequest.getData().getIsPrivate() : false)
                .bio(userProfileRequest.getData().getBio())
                .statusMessage(userProfileRequest.getData().getStatusMessage())
                .build();

    }

    // This method will convert UserProfile entity to UserProfileResponse DTO
    // and return the UserProfileResponse DTO.
    public UserProfileResponse toUserProfileResponse(UserProfile userProfile) throws SQLException {
        if (userProfile == null) {
            return null;
        }

        return UserProfileResponse.builder()
                .userId(userProfile.getUserId())
                .firstName(userProfile.getFirstName())
                .lastName(userProfile.getLastName())
                .isPrivate(userProfile.isPrivate())
                .statusMessage(userProfile.getStatusMessage())
//                .avatar(userProfile.getAvatar() != null ? userProfile.getAvatar().getBytes(1, (int) userProfile.getAvatar().length()) : null)
                .bio(userProfile.getBio())
                .build();


    }

    public void updateUserProfileFromRequest(UserProfile existingProfile, UserProfileRequest userProfileRequest) {
        if (userProfileRequest.getData() != null) {
            if (userProfileRequest.getData().getFirstName() != null) {
                existingProfile.setFirstName(userProfileRequest.getData().getFirstName());
            }
            if (userProfileRequest.getData().getLastName() != null) {
                existingProfile.setLastName(userProfileRequest.getData().getLastName());
            }
            if (userProfileRequest.getData().getBio() != null) {
                existingProfile.setBio(userProfileRequest.getData().getBio());
            }
            if (userProfileRequest.getData().getAvatar() != null) {
                existingProfile.setAvatar(userProfileRequest.getData().getAvatar());
            }
            if (userProfileRequest.getData().getIsPrivate() != null) {
                existingProfile.setPrivate(userProfileRequest.getData().getIsPrivate());
            }
            if (userProfileRequest.getData().getStatusMessage() != null) {
                existingProfile.setStatusMessage(userProfileRequest.getData().getStatusMessage());
            }
        }
    }
}