package com.it.telescopeplatform.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.it.telescopeplatform.order.models.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
