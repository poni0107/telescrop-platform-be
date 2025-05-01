package com.it.telescopeplatform.user.api;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.it.telescopeplatform.auth.dtos.UserDto;
import com.it.telescopeplatform.auth.mappers.UserMapper;
import com.it.telescopeplatform.user.dtos.UpdateUserDto;
import com.it.telescopeplatform.user.dtos.UserFavoriteRequestDto;
import com.it.telescopeplatform.user.dtos.UserFavoriteResponseDto;
import com.it.telescopeplatform.user.mappers.UserFavoriteMapper;
import com.it.telescopeplatform.user.services.UserFavoriteService;
import com.it.telescopeplatform.user.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserFavoriteService userFavoriteService;

    @PatchMapping
    @PreAuthorize("hasAnyRole('Admin', 'Customer')")
    public UserDto updateUser(@RequestBody UpdateUserDto updateUserDto) {
        return UserMapper.toDto(userService.updateUser(updateUserDto));
    }

    @GetMapping("/favorite")
    @PreAuthorize("hasAnyRole('Customer')")
    public List<UserFavoriteResponseDto> getFavorites() {
        return UserFavoriteMapper.toDto(userFavoriteService.getUserFavorites());
    }

    @PostMapping("/favorite")
    @PreAuthorize("hasAnyRole('Customer')")
    public UserFavoriteResponseDto addToFavorites(@RequestBody UserFavoriteRequestDto userFavoriteDto) {
        return UserFavoriteMapper.toDto(userFavoriteService.addToFavorites(userFavoriteDto.getTelescopeId()));
    }

    @PostMapping("/unfavorite")
    @PreAuthorize("hasAnyRole('Customer')")
    public void removeFromFavorites(@RequestBody UserFavoriteRequestDto userFavoriteDto) {
        userFavoriteService.removeFromFavorites(userFavoriteDto.getTelescopeId());
    }
}
