package com.it.telescopeplatform.telescope.dtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TelescopeRequestDto {
    private String name;
    private String description;
    private String brand;
    private String type;
    private BigDecimal aperture;
    private BigDecimal pricePerDay;
    private Boolean available = true;
    private Long publisherId;
}
