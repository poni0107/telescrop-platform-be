package com.it.telescopeplatform.order.dtos;

import com.it.telescopeplatform.order.enums.OrderStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class OrderStatusDto {
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.Pending;
}
