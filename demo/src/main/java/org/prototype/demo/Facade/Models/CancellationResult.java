package org.prototype.demo.Facade.Models;

import java.time.LocalDateTime;

/**
 * Represents the result of a trip cancellation operation.
 */
public class CancellationResult {
    private String bookingId;
    private boolean success;
    private String message;
    private LocalDateTime cancellationDateTime;
    private double refundAmount;

    public CancellationResult(String bookingId, boolean success, String message,
                            LocalDateTime cancellationDateTime, double refundAmount) {
        this.bookingId = bookingId;
        this.success = success;
        this.message = message;
        this.cancellationDateTime = cancellationDateTime;
        this.refundAmount = refundAmount;
    }

    public String getBookingId() {
        return bookingId;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCancellationDateTime() {
        return cancellationDateTime;
    }

    public double getRefundAmount() {
        return refundAmount;
    }
} 