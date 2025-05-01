package com.it.telescopeplatform.telescope.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
    private List<String> imageUrls;
    private String publisherName;
    private Double averageRating;
    private LocalDateTime createdAt;

}
