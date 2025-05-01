package com.it.telescopeplatform.order.api;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.it.telescopeplatform.order.dtos.OrderPaymentDto;
import com.it.telescopeplatform.order.dtos.OrderRequestDto;
import com.it.telescopeplatform.order.dtos.OrderResponseDto;
import com.it.telescopeplatform.order.dtos.OrderStatusDto;
import com.it.telescopeplatform.order.services.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @PreAuthorize("hasAnyRole('Admin', 'Customer')")
    public List<OrderResponseDto> getAll() {
        return orderService.getOrders();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'Customer')")
    public OrderResponseDto getById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('Customer')")
    public OrderResponseDto create(@RequestBody OrderRequestDto order) {
        return orderService.createOrder(order);
    }

    @PatchMapping("/{id}/payment")
    @PreAuthorize("hasRole('Admin')")
    public OrderResponseDto updatePayment(@PathVariable Long id,
            @RequestBody OrderPaymentDto orderPaymentDto) {
        return orderService.updatePayment(id, orderPaymentDto);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('Admin')")
    public OrderResponseDto updateStatus(@PathVariable Long id,
            @RequestBody OrderStatusDto orderStatusDto) {
        return orderService.updateStatus(id, orderStatusDto);
    }
}