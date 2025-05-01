package com.it.telescopeplatform.reservation.services.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.it.telescopeplatform.configs.errorhandlers.ResourceNotFoundException;
import com.it.telescopeplatform.mail.services.MailService;
import com.it.telescopeplatform.reservation.dtos.ReservationPaymentDto;
import com.it.telescopeplatform.reservation.dtos.ReservationRequestDto;
import com.it.telescopeplatform.reservation.dtos.ReservationStatusDto;
import com.it.telescopeplatform.reservation.enums.ReservationStatus;
import com.it.telescopeplatform.reservation.models.Reservation;
import com.it.telescopeplatform.reservation.repositories.ReservationRepository;
import com.it.telescopeplatform.reservation.services.ReservationService;
import com.it.telescopeplatform.telescope.repositories.TelescopeRepository;
import com.it.telescopeplatform.user.enums.UserType;
import com.it.telescopeplatform.user.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final UserService userService;
    private final TelescopeRepository telescopeRepository;
    private final MailService mailService;

    @Override
    public List<Reservation> getReservations() {
        var user = userService.getCurrentUser();
        if (user.getUserType() == UserType.Admin) {
            return reservationRepository.findAll();
        }
        return reservationRepository.findByUser(user);
    }

    @Override
    public Reservation createReservation(ReservationRequestDto reservationDto) {
        var reservation = new Reservation();

        var user = userService.getCurrentUser();
        reservation.setUser(user);

        var telescope = telescopeRepository.findById(reservationDto.getTelescopeId())
                .orElseThrow(() -> new ResourceNotFoundException("Telescope not found"));
        reservation.setTelescope(telescope);
        reservation.setStartDate(reservationDto.getStartDate());
        reservation.setEndDate(reservationDto.getEndDate());
        reservation.setNotes(reservationDto.getNotes());
        // Calculate total cost based on the number of days reserved
        reservation.setTotalCost(telescope.getPricePerDay()
                .multiply(BigDecimal
                        .valueOf(reservation.getEndDate().toEpochDay() - reservation.getStartDate().toEpochDay())));
        reservation.setStatus(ReservationStatus.Pending);

        var savedReservation = reservationRepository.save(reservation);

        var userEmail = user.getEmail();
        var adminEmail = "tosemberger1@gmail.com";

        mailService.sendEmail(userEmail, "Vaša rezervacija je uspešna",
                "Rezervacija #" + savedReservation.getId() + " je uspešno kreirana.");
        mailService.sendEmail(adminEmail, "Nova rezervacija primljena",
                "Nova rezervacija #" + savedReservation.getId() + " od korisnika " + user.getUsername());

        return savedReservation;

    }

    @Override
    public Reservation getReservationById(Long id) {
        var user = userService.getCurrentUser();
        if (user.getUserType() == UserType.Admin) {
            return reservationRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        }
        return reservationRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
    }

    @Override
    public Reservation updatePayment(Long id, ReservationPaymentDto reservationPaymentDto) {
        var reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        reservation.setPaymentStatus(reservationPaymentDto.getPaymentStatus());
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation updateStatus(Long id, ReservationStatusDto reservationStatusDto) {
        var reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        reservation.setStatus(reservationStatusDto.getStatus());
        return reservationRepository.save(reservation);
    }
}