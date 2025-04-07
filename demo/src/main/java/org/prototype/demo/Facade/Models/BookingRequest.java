package org.prototype.demo.Facade.Models;


public class BookingRequest {
    private String tripId;
    private int passengerCount;
    private String contactEmail;
    private String contactPhone;

    public BookingRequest(String tripId, int passengerCount, String contactEmail, String contactPhone) {
        this.tripId = tripId;
        this.passengerCount = passengerCount;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
    }

    public String getTripId() {
        return tripId;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }
} 