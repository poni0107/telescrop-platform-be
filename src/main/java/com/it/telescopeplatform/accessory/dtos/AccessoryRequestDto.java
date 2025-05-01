package com.it.telescopeplatform.accessory.dtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AccessoryRequestDto {
    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private Integer stockQuantity;
    private Long publisherId;
}
