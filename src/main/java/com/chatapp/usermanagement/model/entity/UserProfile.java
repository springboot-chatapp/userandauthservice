package com.chatapp.usermanagement.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserProfile {

    @Id
    private String userId;
    private String firstName;
    private String lastName;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] avatar;

    private String bio;
    private boolean isPrivate;
    private String statusMessage;

}