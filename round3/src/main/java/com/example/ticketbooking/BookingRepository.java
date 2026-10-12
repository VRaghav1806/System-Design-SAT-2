package com.example.ticketbooking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerName(String customerName);

    List<Booking> findByEventId(Long eventId);

    List<Booking> findByStatus(String status);
}
