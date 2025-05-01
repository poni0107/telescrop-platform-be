package com.it.telescopeplatform.order.services;

import java.util.List;

import com.it.telescopeplatform.order.dtos.OrderPaymentDto;
import com.it.telescopeplatform.order.dtos.OrderRequestDto;
import com.it.telescopeplatform.order.dtos.OrderResponseDto;
import com.it.telescopeplatform.order.dtos.OrderStatusDto;

public interface OrderService {
    List<OrderResponseDto> getOrders();

    OrderResponseDto getOrderById(Long id);

    OrderResponseDto createOrder(OrderRequestDto orderDto);

    OrderResponseDto updatePayment(Long id, OrderPaymentDto orderPaymentDto);

    OrderResponseDto updateStatus(Long id, OrderStatusDto orderStatusDto);
}