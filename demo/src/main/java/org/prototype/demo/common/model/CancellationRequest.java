package org.prototype.demo.common.model;

public class CancellationRequest {
    private String bookingId;
    private String reason;

    public CancellationRequest(String bookingId, String reason) {
        this.bookingId = bookingId;
        this.reason = reason;
    }

    // Getters and setters
    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }
    
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
} 