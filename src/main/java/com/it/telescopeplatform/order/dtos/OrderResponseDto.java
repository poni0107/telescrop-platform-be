package com.it.telescopeplatform.order.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.it.telescopeplatform.accessory.dtos.AccessoryResponseDto;
import com.it.telescopeplatform.auth.dtos.UserDto;
import com.it.telescopeplatform.order.enums.OrderStatus;
import com.it.telescopeplatform.order.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {
    private Long id;
    private UserDto user;
    private List<OrderItemResponseDto> orderItems;
    private AccessoryResponseDto accessory;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private PaymentStatus paymentStatus;
    private LocalDateTime createdAt;
}
