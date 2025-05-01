package com.it.telescopeplatform.user.dtos;

import lombok.Data;

@Data
public class UpdateUserDto {
    private String fullName;
    private String phoneNumber;
    private String address;
    private String city;
    private String zipCode;
}
