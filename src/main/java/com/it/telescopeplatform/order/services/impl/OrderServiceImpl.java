package com.it.telescopeplatform.order.services.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.it.telescopeplatform.accessory.repositories.AccessoryRepository;
import com.it.telescopeplatform.configs.errorhandlers.ResourceNotFoundException;
import com.it.telescopeplatform.mail.services.MailService;
import com.it.telescopeplatform.order.dtos.OrderPaymentDto;
import com.it.telescopeplatform.order.dtos.OrderRequestDto;
import com.it.telescopeplatform.order.dtos.OrderResponseDto;
import com.it.telescopeplatform.order.dtos.OrderStatusDto;
import com.it.telescopeplatform.order.enums.OrderStatus;
import com.it.telescopeplatform.order.mappers.OrderMapper;
import com.it.telescopeplatform.order.models.Order;
import com.it.telescopeplatform.order.models.OrderItem;
import com.it.telescopeplatform.order.repositories.OrderItemRepository;
import com.it.telescopeplatform.order.repositories.OrderRepository;
import com.it.telescopeplatform.order.services.OrderService;
import com.it.telescopeplatform.user.enums.UserType;
import com.it.telescopeplatform.user.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private final UserService userService;
    private final AccessoryRepository accessoryRepository;
    private final MailService mailService;

    @Value("${spring.mail.username}")
    private String adminEmail;

    @Override
    public List<OrderResponseDto> getOrders() {
        var user = userService.getCurrentUser();
        if (user.getUserType() == UserType.Admin) {
            return OrderMapper.toDto(orderRepository.findAll());
        }
        return OrderMapper.toDto(orderRepository.findByUser(user));
    }

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderDto) {
        var order = new Order();
        var user = userService.getCurrentUser();
        order.setUser(user);

        var orderItems = orderDto.getItems().stream()
                .map(item -> {
                    var orderItem = new OrderItem();
                    orderItem.setAccessory(accessoryRepository.findById(item.getAccessoryId()).orElseThrow());
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setOrder(order);
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setTotalPrice(orderItems.stream()
                .map(item -> item.getAccessory().getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        order.setStatus(OrderStatus.Pending);

        var savedOrder = orderRepository.save(order);

        var userEmail = user.getEmail();

        mailService.sendEmail(userEmail, "Vaša porudžbina je uspešna",
                "Porudžbina #" + savedOrder.getId() + " je uspešno kreirana.");
        mailService.sendEmail(adminEmail, "Nova porudžbina primljena",
                "Nova porudžbina #" + savedOrder.getId() + " od korisnika " + user.getEmail());

        return OrderMapper.toDto(savedOrder);
    }

    @Override
    public OrderResponseDto updatePayment(Long id, OrderPaymentDto orderPaymentDto) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        order.setPaymentStatus(orderPaymentDto.getPaymentStatus());
        return OrderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public OrderResponseDto updateStatus(Long id, OrderStatusDto orderStatusDto) {
        var order = orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Order not found with id: " + id));
        order.setStatus(orderStatusDto.getStatus());
        return OrderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public OrderResponseDto getOrderById(Long id) {
        var user = userService.getCurrentUser();
        Order order;
        if (user.getUserType() == UserType.Admin) {
            order = orderRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
            return OrderMapper.toDto(order);
        }
        order = orderRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return OrderMapper.toDto(order);
    }
}