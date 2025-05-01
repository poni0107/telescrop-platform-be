package com.it.telescopeplatform.reservation.dtos;

import com.it.telescopeplatform.reservation.enums.ReservationStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ReservationStatusDto {
    @Enumerated(EnumType.STRING)
    private ReservationStatus status = ReservationStatus.Pending;
}
