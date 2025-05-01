package com.it.telescopeplatform.order.dtos;

import java.util.List;

import lombok.Data;

@Data
public class OrderRequestDto {
    private List<OrderItemRequestDto> items;
}