package com.it.telescopeplatform.reservation.api;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.it.telescopeplatform.reservation.dtos.ReservationPaymentDto;
import com.it.telescopeplatform.reservation.dtos.ReservationRequestDto;
import com.it.telescopeplatform.reservation.dtos.ReservationResponseDto;
import com.it.telescopeplatform.reservation.dtos.ReservationStatusDto;
import com.it.telescopeplatform.reservation.mappers.ReservationMapper;
import com.it.telescopeplatform.reservation.services.ReservationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    @PreAuthorize("hasAnyRole('Admin', 'Customer')")
    public List<ReservationResponseDto> getAll() {
        return ReservationMapper.toDto(reservationService.getReservations());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'Customer')")
    public ReservationResponseDto getById(@PathVariable Long id) {
        return ReservationMapper.toDto(reservationService.getReservationById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('Customer')")
    public ReservationResponseDto create(@RequestBody ReservationRequestDto reservation) {
        return ReservationMapper.toDto(reservationService.createReservation(reservation));
    }

    @PatchMapping("/{id}/payment")
    @PreAuthorize("hasRole('Admin')")
    public ReservationResponseDto updatePayment(@PathVariable Long id,
            @RequestBody ReservationPaymentDto reservationPaymentDto) {
        return ReservationMapper.toDto(reservationService.updatePayment(id, reservationPaymentDto));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('Admin')")
    public ReservationResponseDto updateStatus(@PathVariable Long id,
            @RequestBody ReservationStatusDto reservationStatusDto) {
        return ReservationMapper.toDto(reservationService.updateStatus(id, reservationStatusDto));
    }
}