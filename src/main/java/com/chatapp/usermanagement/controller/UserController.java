package com.chatapp.usermanagement.controller;

import com.chatapp.usermanagement.exception.UserManagementException;
import com.chatapp.usermanagement.exception.handler.ErrorType;
import com.chatapp.usermanagement.mapper.ResponseMapper;
import com.chatapp.usermanagement.model.dto.request.UserProfileRequest;
import com.chatapp.usermanagement.model.dto.response.BaseResponse;
import com.chatapp.usermanagement.model.dto.response.UserProfileResponse;
import com.chatapp.usermanagement.service.UserProfileService;
import com.chatapp.usermanagement.validator.UserProfileValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserProfileService userProfileService;
    @Autowired
    UserProfileValidator userProfileValidator;

    @Autowired
    ObjectMapper objectMapper;
    @PostMapping(value = "/userProfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<UserProfileResponse>> createUserProfile(
            @RequestHeader(value = "X-Correlation-ID", required = true) String correlationId,
            @RequestParam("data") String jsonData, // JSON as string
            @RequestParam(value = "avatar", required = false) MultipartFile avatar,
            final HttpServletRequest httpServletRequest) throws SQLException, IOException {

        // Parse JSON data
        UserProfileRequest userProfileRequest = objectMapper.readValue(jsonData, UserProfileRequest.class);

        // Set avatar if provided
        if (avatar != null && userProfileRequest.getData() != null) {
            userProfileRequest.getData().setAvatar(avatar.getBytes());
        }

        userProfileValidator.validateUserProfileRequest(userProfileRequest);
        UserProfileResponse response = userProfileService.createUserProfile(userProfileRequest);

        return ResponseMapper.getResponseEntity(httpServletRequest, response, ErrorType.USER_PROFILE_CREATED);
    }
        @GetMapping("/userProfile")
        public ResponseEntity<BaseResponse<List<UserProfileResponse>>> getUserProfile(
                @RequestHeader(value = "X-Correlation-ID", required = true)  String correlationId,
                final HttpServletRequest httpServletRequest) throws SQLException {
            List<UserProfileResponse> userProfilesList;

            userProfilesList = userProfileService.getAllUses();
            if (userProfilesList.isEmpty()) {
                return ResponseMapper.getResponseEntity(httpServletRequest, userProfilesList, ErrorType.NO_DATA_FOUND);
            }

            return ResponseMapper.getResponseEntity(httpServletRequest,userProfilesList, ErrorType.GET_USER_PROFILE_SUCCESS);
        }


    @GetMapping("/userProfile/{userId}")
    public ResponseEntity<BaseResponse<UserProfileResponse>> getUserProfile(
            @RequestHeader(value = "X-Correlation-ID", required = true)  String correlationId,
            @PathVariable("userId") String userId,
            final HttpServletRequest httpServletRequest) throws SQLException {


        UserProfileResponse userProfile = userProfileService.getUserProfile(userId);

        return ResponseMapper.getResponseEntity(httpServletRequest,userProfile, ErrorType.GET_USER_PROFILE_SUCCESS);
    }

    @PatchMapping(value = "/userProfile/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<UserProfileResponse>> updateUserProfile(
            @PathVariable(value = "userId", required = false)String userId,
            @RequestParam("data") String jsonData, // JSON as string
            @RequestParam(value = "avatar", required = false) MultipartFile avatar,
            final HttpServletRequest httpServletRequest) throws SQLException, IOException {

        if (userId == null || userId.isEmpty()) {
            throw new UserManagementException(ErrorType.MESSAGE_NOT_READABLE, "User ID cannot be null or empty");
        }

        UserProfileRequest userProfileRequest = objectMapper.readValue(jsonData, UserProfileRequest.class);
        if (avatar != null && userProfileRequest.getData() != null) {
            userProfileRequest.getData().setAvatar(avatar.getBytes());
        }
        userProfileValidator.validateUserProfileRequestForUpdate(userProfileRequest);
        UserProfileResponse response = userProfileService.updateUserProfile(userId,userProfileRequest);

        return ResponseMapper.getResponseEntity(httpServletRequest, response, ErrorType.USER_PROFILE_UPDATED);
    }
}