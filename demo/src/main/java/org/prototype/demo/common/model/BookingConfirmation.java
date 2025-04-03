package org.prototype.demo.common.model;

public class BookingConfirmation {
    private String bookingId;
    private String tripId;
    private String status;
    private String confirmationMessage;

    public BookingConfirmation(String bookingId, String tripId, String status, String confirmationMessage) {
        this.bookingId = bookingId;
        this.tripId = tripId;
        this.status = status;
        this.confirmationMessage = confirmationMessage;
    }

    // Getters and setters
    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }
    
    public String getTripId() { return tripId; }
    public void setTripId(String tripId) { this.tripId = tripId; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getConfirmationMessage() { return confirmationMessage; }
    public void setConfirmationMessage(String confirmationMessage) { this.confirmationMessage = confirmationMessage; }
} 