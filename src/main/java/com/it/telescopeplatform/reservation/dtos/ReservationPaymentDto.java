package com.it.telescopeplatform.reservation.dtos;

import com.it.telescopeplatform.reservation.enums.PaymentStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ReservationPaymentDto {
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.Pending;
}