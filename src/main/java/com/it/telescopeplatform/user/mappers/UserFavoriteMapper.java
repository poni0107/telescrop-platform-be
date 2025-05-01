package com.it.telescopeplatform.user.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.it.telescopeplatform.telescope.mappers.TelescopeMapper;
import com.it.telescopeplatform.user.dtos.UserFavoriteResponseDto;
import com.it.telescopeplatform.user.models.UserFavorite;

public class UserFavoriteMapper {
    public static UserFavoriteResponseDto toDto(UserFavorite userFavorite) {
        if (userFavorite == null) {
            return null;
        }

        return UserFavoriteResponseDto.builder()
                .telescope(TelescopeMapper.toDto(userFavorite.getTelescopeId()))
                .addedAt(userFavorite.getAddedAt())
                .build();
    }

    public static List<UserFavoriteResponseDto> toDto(List<UserFavorite> userFavorites) {
        if (userFavorites == null || userFavorites.isEmpty())
            return List.of();

        return userFavorites.stream()
                .map(UserFavoriteMapper::toDto)
                .collect(Collectors.toList());
    }
}
