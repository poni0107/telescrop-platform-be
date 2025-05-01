package com.it.telescopeplatform.user.dtos;

import java.time.LocalDateTime;

import com.it.telescopeplatform.telescope.dtos.TelescopeResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFavoriteResponseDto {
    private TelescopeResponseDto telescope;
    private LocalDateTime addedAt;
}
