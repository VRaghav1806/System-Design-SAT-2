package com.example.ticketbooking;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping({"/bookings", "/api/bookings"})
public class BookingController {

    private final Map<Long, Booking> bookingStore = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public BookingController() {
        // Pre-populate with initial sample booking for testing convenience
        long sampleId = idGenerator.getAndIncrement();
        bookingStore.put(sampleId, new Booking(
                sampleId,
                "John Doe",
                "Avengers: Endgame",
                2,
                24.50,
                "CONFIRMED",
                LocalDateTime.now()
        ));
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookingStore.values());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        Booking booking = bookingStore.get(id);
        if (booking != null) {
            return ResponseEntity.ok(booking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        long newId = idGenerator.getAndIncrement();
        booking.setId(newId);
        if (booking.getBookingTime() == null) {
            booking.setBookingTime(LocalDateTime.now());
        }
        if (booking.getStatus() == null || booking.getStatus().isBlank()) {
            booking.setStatus("CONFIRMED");
        }
        bookingStore.put(newId, booking);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking updatedBooking) {
        Booking existing = bookingStore.get(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        if (updatedBooking.getCustomerName() != null) {
            existing.setCustomerName(updatedBooking.getCustomerName());
        }
        if (updatedBooking.getEventName() != null) {
            existing.setEventName(updatedBooking.getEventName());
        }
        if (updatedBooking.getNumberOfSeats() > 0) {
            existing.setNumberOfSeats(updatedBooking.getNumberOfSeats());
        }
        if (updatedBooking.getTotalAmount() > 0) {
            existing.setTotalAmount(updatedBooking.getTotalAmount());
        }
        if (updatedBooking.getStatus() != null) {
            existing.setStatus(updatedBooking.getStatus());
        }

        bookingStore.put(id, existing);
        return ResponseEntity.ok(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        Booking removed = bookingStore.remove(id);
        if (removed != null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
