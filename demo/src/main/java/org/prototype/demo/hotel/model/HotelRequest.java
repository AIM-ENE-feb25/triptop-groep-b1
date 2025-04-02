package org.prototype.demo.hotel.model;


public class HotelRequest {
    private String location;
    private DateRange dates;

    public HotelRequest(String location, DateRange dates) {
        this.location = location;
        this.dates = dates;
    }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public DateRange getDates() { return dates; }
    public void setDates(DateRange dates) { this.dates = dates; }
} 