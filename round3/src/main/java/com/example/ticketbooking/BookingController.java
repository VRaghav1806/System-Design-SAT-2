package com.example.ticketbooking;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"/bookings", "/api/bookings"})
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest request) {
        try {
            Booking booking = bookingService.createBooking(
                    request.getEventId(),
                    request.getCustomerName(),
                    request.getCustomerEmail(),
                    request.getSeatNumbers()
            );
            return new ResponseEntity<>(booking, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id) {
        try {
            Booking cancelled = bookingService.cancelBooking(id);
            return ResponseEntity.ok(cancelled);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelBookingViaPut(@PathVariable Long id) {
        return cancelBooking(id);
    }

    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return bookingService.getAllEvents();
    }

    @PostMapping("/events")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event created = bookingService.createEvent(event);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/events/{eventId}/seats")
    public List<Seat> getAvailableSeats(@PathVariable Long eventId) {
        return bookingService.getAvailableSeats(eventId);
    }

    // Static helper DTO for booking creation requests
    public static class BookingRequest {
        private Long eventId;
        private String customerName;
        private String customerEmail;
        private List<String> seatNumbers;

        public BookingRequest() {
        }

        public BookingRequest(Long eventId, String customerName, String customerEmail, List<String> seatNumbers) {
            this.eventId = eventId;
            this.customerName = customerName;
            this.customerEmail = customerEmail;
            this.seatNumbers = seatNumbers;
        }

        public Long getEventId() {
            return eventId;
        }

        public void setEventId(Long eventId) {
            this.eventId = eventId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerEmail() {
            return customerEmail;
        }

        public void setCustomerEmail(String customerEmail) {
            this.customerEmail = customerEmail;
        }

        public List<String> getSeatNumbers() {
            return seatNumbers;
        }

        public void setSeatNumbers(List<String> seatNumbers) {
            this.seatNumbers = seatNumbers;
        }
    }
}
