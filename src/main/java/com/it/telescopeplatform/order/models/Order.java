package com.it.telescopeplatform.order.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.it.telescopeplatform.order.enums.OrderStatus;
import com.it.telescopeplatform.order.enums.PaymentStatus;
import com.it.telescopeplatform.user.models.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.Pending;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.Pending;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}