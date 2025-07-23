package com.chatapp.usermanagement.reposiroty;

import com.chatapp.usermanagement.model.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, String> {
    Optional<UserProfile> findById(String userId);
}