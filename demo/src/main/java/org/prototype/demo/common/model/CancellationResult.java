package org.prototype.demo.common.model;

public class CancellationResult {
    private String bookingId;
    private String status;
    private String cancellationMessage;
    private double refundAmount;

    public CancellationResult(String bookingId, String status, String cancellationMessage, double refundAmount) {
        this.bookingId = bookingId;
        this.status = status;
        this.cancellationMessage = cancellationMessage;
        this.refundAmount = refundAmount;
    }

    // Getters and setters
    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getCancellationMessage() { return cancellationMessage; }
    public void setCancellationMessage(String cancellationMessage) { this.cancellationMessage = cancellationMessage; }
    
    public double getRefundAmount() { return refundAmount; }
    public void setRefundAmount(double refundAmount) { this.refundAmount = refundAmount; }
} 