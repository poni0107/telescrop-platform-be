package com.it.telescopeplatform.user.services.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.it.telescopeplatform.configs.errorhandlers.ResourceNotFoundException;
import com.it.telescopeplatform.user.dtos.UpdateUserDto;
import com.it.telescopeplatform.user.models.User;
import com.it.telescopeplatform.user.repositories.UserRepository;
import com.it.telescopeplatform.user.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var email = auth.getName();
        return userRepository.findByUsername(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User updateUser(UpdateUserDto userDto) {
        var user = getCurrentUser();
        user.setFullName(userDto.getFullName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAddress(userDto.getAddress());
        user.setCity(userDto.getCity());
        user.setZipCode(userDto.getZipCode());

        return userRepository.save(user);
    }

}
