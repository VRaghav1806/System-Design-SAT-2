package com.example.ticketbooking;

import java.time.LocalDateTime;

public class Booking {
    private Long id;
    private String customerName;
    private String eventName;
    private int numberOfSeats;
    private double totalAmount;
    private String status;
    private LocalDateTime bookingTime;

    public Booking() {
        this.bookingTime = LocalDateTime.now();
        this.status = "CONFIRMED";
    }

    public Booking(Long id, String customerName, String eventName, int numberOfSeats, double totalAmount) {
        this.id = id;
        this.customerName = customerName;
        this.eventName = eventName;
        this.numberOfSeats = numberOfSeats;
        this.totalAmount = totalAmount;
        this.status = "CONFIRMED";
        this.bookingTime = LocalDateTime.now();
    }

    public Booking(Long id, String customerName, String eventName, int numberOfSeats, double totalAmount, String status, LocalDateTime bookingTime) {
        this.id = id;
        this.customerName = customerName;
        this.eventName = eventName;
        this.numberOfSeats = numberOfSeats;
        this.totalAmount = totalAmount;
        this.status = status;
        this.bookingTime = bookingTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", eventName='" + eventName + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                ", bookingTime=" + bookingTime +
                '}';
    }
}
