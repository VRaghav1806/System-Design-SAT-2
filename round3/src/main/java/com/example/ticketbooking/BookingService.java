package com.example.ticketbooking;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookingService {

    private final EventRepository eventRepository;
    private final SeatRepository seatRepository;
    private final BookingRepository bookingRepository;

    public BookingService(EventRepository eventRepository,
                          SeatRepository seatRepository,
                          BookingRepository bookingRepository) {
        this.eventRepository = eventRepository;
        this.seatRepository = seatRepository;
        this.bookingRepository = bookingRepository;
    }

    @PostConstruct
    public void initSampleData() {
        if (eventRepository.count() == 0) {
            Event sampleEvent = new Event(
                    "Grand Music Concert",
                    "City Arena Hall A",
                    LocalDateTime.now().plusDays(7),
                    75.0,
                    20
            );
            sampleEvent = eventRepository.save(sampleEvent);

            List<Seat> seats = new ArrayList<>();
            for (int i = 1; i <= sampleEvent.getTotalSeats(); i++) {
                String seatNum = (i <= 10 ? "A" : "B") + (i <= 10 ? i : (i - 10));
                seats.add(new Seat(seatNum, false, sampleEvent));
            }
            seatRepository.saveAll(seats);

            // Create initial sample booking for convenience
            List<String> bookedSeats = List.of("A1", "A2");
            createBooking(sampleEvent.getId(), "Alice Smith", "alice@example.com", bookedSeats);
        }
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long eventId) {
        return eventRepository.findById(eventId);
    }

    public Event createEvent(Event event) {
        Event saved = eventRepository.save(event);
        if (event.getTotalSeats() > 0) {
            List<Seat> seats = new ArrayList<>();
            for (int i = 1; i <= event.getTotalSeats(); i++) {
                String seatNum = "S" + i;
                seats.add(new Seat(seatNum, false, saved));
            }
            seatRepository.saveAll(seats);
        }
        return saved;
    }

    public List<Seat> getAvailableSeats(Long eventId) {
        return seatRepository.findByEventIdAndIsBooked(eventId, false);
    }

    public List<Seat> getAllSeatsByEvent(Long eventId) {
        return seatRepository.findByEventId(eventId);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public Booking createBooking(Long eventId, String customerName, String customerEmail, List<String> seatNumbers) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with ID: " + eventId));

        if (seatNumbers == null || seatNumbers.isEmpty()) {
            throw new IllegalArgumentException("At least one seat must be selected for booking.");
        }

        List<Seat> seatsToBook = seatRepository.findByEventIdAndSeatNumberIn(eventId, seatNumbers);

        if (seatsToBook.size() != seatNumbers.size()) {
            throw new IllegalArgumentException("One or more requested seat numbers do not exist for this event.");
        }

        for (Seat seat : seatsToBook) {
            if (seat.isBooked()) {
                throw new IllegalStateException("Seat " + seat.getSeatNumber() + " is already booked.");
            }
        }

        double totalAmount = seatsToBook.size() * event.getTicketPrice();

        Booking booking = new Booking(customerName, customerEmail, event, totalAmount);
        Booking savedBooking = bookingRepository.save(booking);

        for (Seat seat : seatsToBook) {
            seat.setBooked(true);
            seat.setBooking(savedBooking);
        }
        seatRepository.saveAll(seatsToBook);

        savedBooking.setSeats(seatsToBook);
        return savedBooking;
    }

    public Booking cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with ID: " + bookingId));

        if ("CANCELLED".equalsIgnoreCase(booking.getStatus())) {
            throw new IllegalStateException("Booking is already cancelled.");
        }

        List<Seat> seats = seatRepository.findByBookingId(bookingId);
        for (Seat seat : seats) {
            seat.setBooked(false);
            seat.setBooking(null);
        }
        seatRepository.saveAll(seats);

        booking.setStatus("CANCELLED");
        return bookingRepository.save(booking);
    }
}
