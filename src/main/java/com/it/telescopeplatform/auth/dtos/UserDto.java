package com.it.telescopeplatform.auth.dtos;

import java.time.LocalDateTime;

import com.it.telescopeplatform.user.enums.UserType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String city;
    private String zipCode;
    private UserType userType;
    private LocalDateTime createdAt;
}
