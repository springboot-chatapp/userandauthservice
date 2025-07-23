package com.chatapp.usermanagement.service;

import com.chatapp.usermanagement.exception.UserManagementException;
import com.chatapp.usermanagement.exception.handler.ErrorType;
import com.chatapp.usermanagement.mapper.Mapper;
import com.chatapp.usermanagement.model.dto.request.UserProfileRequest;
import com.chatapp.usermanagement.model.dto.response.UserProfileResponse;
import com.chatapp.usermanagement.model.entity.UserProfile;
import com.chatapp.usermanagement.reposiroty.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProfileService {

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    Mapper mapper;

    public UserProfileResponse createUserProfile(UserProfileRequest request) throws SQLException {

        UserProfile userProfile = mapper.toUserProfileEntity(request);
        UserProfile savedProfile = userProfileRepository.save(userProfile);

        return mapper.toUserProfileResponse(savedProfile);
    }


    public List<UserProfileResponse> getAllUses() throws SQLException {
        List<UserProfile> userProfile = userProfileRepository.findAll();

//        convert list of UserProfile to list of UserProfileResponse
        List<UserProfileResponse> userProfileResponses = userProfile.stream()
                .map(user -> {
                    try {
                        return mapper.toUserProfileResponse(user);
                    } catch (SQLException e) {
                        throw new RuntimeException("Error converting UserProfile to UserProfileResponse", e);
                    }
                })
                .collect(Collectors.toList());

        return userProfileResponses;
    }

    public UserProfileResponse getUserProfile(String userId) throws SQLException {
        Optional<UserProfile> userProfile = userProfileRepository.findById(userId);

        if(userProfile.isEmpty()) {
            throw new UserManagementException(ErrorType.USER_NOT_FOUND, "User profile not found for userId: " + userId);
        }

        return mapper.toUserProfileResponse(userProfile.get());

    }


    //method to update user profile

    public UserProfileResponse updateUserProfile(String userId, UserProfileRequest userProfileRequest) throws SQLException {
        Optional<UserProfile> userProfile = userProfileRepository.findById(userId);

        if(userProfile.isEmpty()) {

            throw new UserManagementException(ErrorType.USER_NOT_FOUND, "User profile not found for userId: " + userId);
        }
        UserProfile existingProfile = userProfile.get();
        mapper.updateUserProfileFromRequest(existingProfile, userProfileRequest);
        UserProfile updatedProfile = userProfileRepository.save(existingProfile);
        return mapper.toUserProfileResponse(updatedProfile);
    }
}