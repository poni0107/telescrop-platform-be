package com.it.telescopeplatform.telescope.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.it.telescopeplatform.image.dtos.ImageResponseDto;
import com.it.telescopeplatform.review.dtos.ReviewResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TelescopeResponseDto {
    private Long id;
    private String name;
    private String description;
    private String brand;
    private String type;
    private BigDecimal aperture;
    private BigDecimal pricePerDay;
    private Boolean available;
    private List<ImageResponseDto> images;
    private Long publisherId;
    private Double averageRating;
    private List<ReviewResponseDto> reviews;
    private LocalDateTime createdAt;

}
