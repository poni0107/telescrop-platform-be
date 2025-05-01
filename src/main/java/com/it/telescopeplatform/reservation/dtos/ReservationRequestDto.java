package com.it.telescopeplatform.reservation.dtos;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReservationRequestDto {
    private Long telescopeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String notes;
}