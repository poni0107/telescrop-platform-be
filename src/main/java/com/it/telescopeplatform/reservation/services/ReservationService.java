package com.it.telescopeplatform.reservation.services;

import java.util.List;

import com.it.telescopeplatform.reservation.dtos.ReservationPaymentDto;
import com.it.telescopeplatform.reservation.dtos.ReservationRequestDto;
import com.it.telescopeplatform.reservation.dtos.ReservationStatusDto;
import com.it.telescopeplatform.reservation.models.Reservation;

public interface ReservationService {
    List<Reservation> getReservations();

    Reservation getReservationById(Long id);

    Reservation createReservation(ReservationRequestDto reservationDto);

    Reservation updatePayment(Long id, ReservationPaymentDto reservationPaymentDto);

    Reservation updateStatus(Long id, ReservationStatusDto reservationStatusDto);
}