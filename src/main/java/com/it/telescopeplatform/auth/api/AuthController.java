package com.it.telescopeplatform.auth.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.it.telescopeplatform.auth.dtos.LoginResponse;
import com.it.telescopeplatform.auth.dtos.LoginUserDto;
import com.it.telescopeplatform.auth.dtos.RegisterUserDto;
import com.it.telescopeplatform.auth.dtos.UserDto;
import com.it.telescopeplatform.auth.mappers.UserMapper;
import com.it.telescopeplatform.auth.services.AuthService;
import com.it.telescopeplatform.configs.JwtService;
import com.it.telescopeplatform.user.models.User;

import lombok.RequiredArgsConstructor;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final JwtService jwtService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> register(@RequestBody RegisterUserDto registerUserDto) {
        try {
            var registeredUser = UserMapper.toDto(authService.signup(registerUserDto));
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginUserDto loginUserDto) {
        try {
            var authenticatedUser = authService.authenticate(loginUserDto);
            var jwtToken = jwtService.generateToken(authenticatedUser);
            var loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('Customer','Admin')")
    public ResponseEntity<UserDto> authenticatedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var currentUser = UserMapper.toDto((User) authentication.getPrincipal());
        return ResponseEntity.ok(currentUser);
    }
}
