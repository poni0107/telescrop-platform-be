package com.it.telescopeplatform.auth.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.it.telescopeplatform.auth.dtos.LoginUserDto;
import com.it.telescopeplatform.auth.dtos.RegisterUserDto;
import com.it.telescopeplatform.user.enums.UserType;
import com.it.telescopeplatform.user.models.User;
import com.it.telescopeplatform.user.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public User signup(RegisterUserDto input) {
        var user = new User();
        user.setUsername(input.username());
        user.setEmail(input.email());
        user.setPasswordHash(passwordEncoder.encode(input.password()));
        user.setUserType(UserType.Customer);
        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.username(),
                        input.password()));

        return userRepository.findByUsername(input.username())
                .orElseThrow();
    }
}
