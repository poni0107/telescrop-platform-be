package com.it.telescopeplatform.reservation.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.it.telescopeplatform.reservation.models.Reservation;
import com.it.telescopeplatform.user.models.User;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser(User user);

    Optional<Reservation> findByIdAndUser(Long id, User user);

    Optional<List<Reservation>> findByUserIdAndTelescopeId(Long userId, Long telescopeId);
}