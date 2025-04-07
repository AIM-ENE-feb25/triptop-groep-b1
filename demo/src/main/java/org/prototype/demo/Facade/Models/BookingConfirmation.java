package org.prototype.demo.Facade.Models;

import java.time.LocalDateTime;

public class BookingConfirmation {
    private String bookingId;
    private String tripId;
    private int passengerCount;
    private double totalPrice;
    private LocalDateTime bookingDateTime;
    private String provider;

    public BookingConfirmation(String bookingId, String tripId, int passengerCount,
                             double totalPrice, LocalDateTime bookingDateTime, String provider) {
        this.bookingId = bookingId;
        this.tripId = tripId;
        this.passengerCount = passengerCount;
        this.totalPrice = totalPrice;
        this.bookingDateTime = bookingDateTime;
        this.provider = provider;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getTripId() {
        return tripId;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getBookingDateTime() {
        return bookingDateTime;
    }

    public String getProvider() {
        return provider;
    }
} 