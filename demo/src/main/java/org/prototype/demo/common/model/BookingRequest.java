package org.prototype.demo.common.model;

public class BookingRequest {
    private String tripId;
    private int numberOfGuests;
    private String customerName;
    private String customerEmail;

    public BookingRequest(String tripId, int numberOfGuests, String customerName, String customerEmail) {
        this.tripId = tripId;
        this.numberOfGuests = numberOfGuests;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
    }

    // Getters and setters
    public String getTripId() { return tripId; }
    public void setTripId(String tripId) { this.tripId = tripId; }
    
    public int getNumberOfGuests() { return numberOfGuests; }
    public void setNumberOfGuests(int numberOfGuests) { this.numberOfGuests = numberOfGuests; }
    
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
} 