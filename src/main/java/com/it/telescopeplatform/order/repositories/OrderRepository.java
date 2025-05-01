package com.it.telescopeplatform.order.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.it.telescopeplatform.order.models.Order;
import com.it.telescopeplatform.user.models.User;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

    Optional<Order> findByIdAndUser(Long id, User user);

    @Query("SELECT DISTINCT o " +
            "FROM Order o " +
            "INNER JOIN o.orderItems oi " +
            "INNER JOIN o.user u " +
            "WHERE oi.accessory.id = :accessoryId AND u.id = :userId")
    Optional<List<Order>> findByUserIdAndAccessoryId(Long userId, Long accessoryId);
}