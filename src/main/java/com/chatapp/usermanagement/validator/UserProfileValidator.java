package com.chatapp.usermanagement.validator;

import com.chatapp.usermanagement.exception.UserManagementException;
import com.chatapp.usermanagement.exception.handler.ErrorType;
import com.chatapp.usermanagement.model.dto.request.UserProfileRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UserProfileValidator {


    public void validateUserProfileRequest(UserProfileRequest userProfileRequest) {
        if (userProfileRequest.getData() != null) {
            if (!StringUtils.hasText(userProfileRequest.getData().getUserId())) {
                throw new UserManagementException(ErrorType.MANDATORY_FIELD_MISSING, "User Id cannot be empty");
            }
            if (!StringUtils.hasText(userProfileRequest.getData().getFirstName())) {
                throw new UserManagementException(ErrorType.MANDATORY_FIELD_MISSING, "First name cannot be empty");
            }
            if (!StringUtils.hasText(userProfileRequest.getData().getLastName())) {
                throw new UserManagementException(ErrorType.MANDATORY_FIELD_MISSING, "Last name cannot be empty");
            }
            if(StringUtils.hasText(userProfileRequest.getData().getBio()) && userProfileRequest.getData().getBio().length() > 500){
                throw new UserManagementException(ErrorType.INVALID_REQUEST , "Bio cannot exceed 500 characters");

            }

        }else{
            throw new UserManagementException(ErrorType.INVALID_REQUEST, "User profile data cannot be null");
        }


    }

    public void validateUserProfileRequestForUpdate(UserProfileRequest userProfileRequest) {
        if (userProfileRequest.getData() != null) {
            if (!StringUtils.hasText(userProfileRequest.getData().getFirstName()) &&
                    !StringUtils.hasText(userProfileRequest.getData().getLastName()) &&
                    !StringUtils.hasText(userProfileRequest.getData().getStatusMessage()) &&
                    !StringUtils.hasText(userProfileRequest.getData().getBio()) &&
                    userProfileRequest.getData().getIsPrivate() == null &&
                    userProfileRequest.getData().getAvatar() == null ) {
                throw new UserManagementException(ErrorType.MANDATORY_FIELD_MISSING, "At least 1 field required to update user profile");
            }
        }else{
            throw new UserManagementException(ErrorType.INVALID_REQUEST, "User profile data cannot be null");
        }
    }
}