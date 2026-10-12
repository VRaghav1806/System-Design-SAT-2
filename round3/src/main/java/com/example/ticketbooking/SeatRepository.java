package com.example.ticketbooking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByEventId(Long eventId);

    List<Seat> findByEventIdAndIsBooked(Long eventId, boolean isBooked);

    List<Seat> findByEventIdAndSeatNumberIn(Long eventId, List<String> seatNumbers);

    List<Seat> findByBookingId(Long bookingId);
}
