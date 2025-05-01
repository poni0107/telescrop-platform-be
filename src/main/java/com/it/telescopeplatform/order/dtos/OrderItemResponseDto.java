package com.it.telescopeplatform.order.dtos;

import com.it.telescopeplatform.accessory.dtos.AccessoryResponseDto;

import lombok.Data;

@Data
public class OrderItemResponseDto {
    private AccessoryResponseDto accessory;
    private Integer quantity;
}
