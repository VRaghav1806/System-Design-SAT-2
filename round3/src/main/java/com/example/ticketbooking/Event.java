package com.example.ticketbooking;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String venue;

    private LocalDateTime eventTime;

    private double ticketPrice;

    private int totalSeats;

    public Event() {
    }

    public Event(Long id, String name, String venue, LocalDateTime eventTime, double ticketPrice, int totalSeats) {
        this.id = id;
        this.name = name;
        this.venue = venue;
        this.eventTime = eventTime;
        this.ticketPrice = ticketPrice;
        this.totalSeats = totalSeats;
    }

    public Event(String name, String venue, LocalDateTime eventTime, double ticketPrice, int totalSeats) {
        this.name = name;
        this.venue = venue;
        this.eventTime = eventTime;
        this.ticketPrice = ticketPrice;
        this.totalSeats = totalSeats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", venue='" + venue + '\'' +
                ", eventTime=" + eventTime +
                ", ticketPrice=" + ticketPrice +
                ", totalSeats=" + totalSeats +
                '}';
    }
}
