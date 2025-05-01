package com.it.telescopeplatform.reservation.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.it.telescopeplatform.auth.dtos.UserDto;
import com.it.telescopeplatform.reservation.enums.PaymentStatus;
import com.it.telescopeplatform.reservation.enums.ReservationStatus;
import com.it.telescopeplatform.telescope.dtos.TelescopeResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponseDto {
    private Long id;
    private UserDto user;
    private TelescopeResponseDto telescope;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalCost;
    private ReservationStatus status;
    private PaymentStatus paymentStatus;
    private String notes;
    private LocalDateTime createdAt;
}
