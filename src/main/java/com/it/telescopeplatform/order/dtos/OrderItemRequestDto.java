package com.it.telescopeplatform.order.dtos;

import lombok.Data;

@Data
public class OrderItemRequestDto {
    private Long accessoryId;
    private Integer quantity;
}