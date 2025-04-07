package org.prototype.demo.Facade.Models;

import java.time.LocalDate;


public class TripResult {
    private String tripId;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private double price;
    private String provider;
    private int availableSpots;

    public TripResult(String tripId, String destination, LocalDate startDate, LocalDate endDate,
                     double price, String provider, int availableSpots) {
        this.tripId = tripId;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.provider = provider;
        this.availableSpots = availableSpots;
    }

    public String getTripId() {
        return tripId;
    }

    public String getDestination() {
        return destination; 
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getPrice() {
        return price;
    }

    public String getProvider() {
        return provider;
    }

    public int getAvailableSpots() {
        return availableSpots;
    }
} 