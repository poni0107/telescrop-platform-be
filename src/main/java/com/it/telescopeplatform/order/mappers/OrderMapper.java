package com.it.telescopeplatform.order.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.it.telescopeplatform.accessory.mappers.AccessoryMapper;
import com.it.telescopeplatform.auth.mappers.UserMapper;
import com.it.telescopeplatform.order.dtos.OrderItemResponseDto;
import com.it.telescopeplatform.order.dtos.OrderResponseDto;
import com.it.telescopeplatform.order.models.Order;
import com.it.telescopeplatform.order.models.OrderItem;

@Component
public interface OrderMapper {

    public static OrderResponseDto toDto(Order order) {
        if (order == null)
            return null;

        return OrderResponseDto.builder()
                .id(order.getId())
                .user(UserMapper.toDto(order.getUser()))
                .orderItems(toOrderItemDtoList(order.getOrderItems()))
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .paymentStatus(order.getPaymentStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }

    public static List<OrderResponseDto> toDto(List<Order> orders) {
        return orders == null ? List.of() : orders.stream().map(OrderMapper::toDto).collect(Collectors.toList());
    }

    private static List<OrderItemResponseDto> toOrderItemDtoList(List<OrderItem> items) {
        if (items == null)
            return List.of();
        return items.stream().map(OrderMapper::toOrderItemDto).collect(Collectors.toList());
    }

    private static OrderItemResponseDto toOrderItemDto(OrderItem item) {
        if (item == null)
            return null;

        var dto = new OrderItemResponseDto();
        dto.setAccessory(AccessoryMapper.toDto(item.getAccessory()));
        dto.setQuantity(item.getQuantity());
        return dto;
    }
}