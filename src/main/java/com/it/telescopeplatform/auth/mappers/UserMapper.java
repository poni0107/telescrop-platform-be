package com.it.telescopeplatform.auth.mappers;

import com.it.telescopeplatform.auth.dtos.UserDto;
import com.it.telescopeplatform.user.models.User;

public class UserMapper {
    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .city(user.getCity())
                .zipCode(user.getZipCode())
                .userType(user.getUserType())
                .createdAt(user.getCreatedAt())
                .build();
    }

}
