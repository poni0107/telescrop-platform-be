package com.it.telescopeplatform.reservation.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.it.telescopeplatform.auth.mappers.UserMapper;
import com.it.telescopeplatform.reservation.dtos.ReservationResponseDto;
import com.it.telescopeplatform.reservation.models.Reservation;
import com.it.telescopeplatform.telescope.mappers.TelescopeMapper;

public interface ReservationMapper {
    public static ReservationResponseDto toDto(Reservation reservation) {
        if (reservation == null)
            return null;

        return ReservationResponseDto.builder()
                .id(reservation.getId())
                .user(UserMapper.toDto(reservation.getUser()))
                .telescope(TelescopeMapper.toDto(reservation.getTelescope()))
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .totalCost(reservation.getTotalCost())
                .status(reservation.getStatus())
                .paymentStatus(reservation.getPaymentStatus())
                .notes(reservation.getNotes())
                .createdAt(reservation.getCreatedAt())
                .build();
    }

    public static List<ReservationResponseDto> toDto(List<Reservation> reservations) {
        if (reservations == null || reservations.isEmpty()) {
            return List.of();
        }

        return reservations.stream()
                .map(ReservationMapper::toDto)
                .collect(Collectors.toList());
    }

}