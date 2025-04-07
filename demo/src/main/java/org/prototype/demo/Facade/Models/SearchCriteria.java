package org.prototype.demo.Facade.Models;

import java.time.LocalDate;


public class SearchCriteria {
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private int passengerCount;
    private double maxPrice;

    public SearchCriteria(String destination, LocalDate startDate, LocalDate endDate, 
                         int passengerCount, double maxPrice) {
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.passengerCount = passengerCount;
        this.maxPrice = maxPrice;
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
 
    public int getPassengerCount() {
        return passengerCount;
    }

    public double getMaxPrice() {
        return maxPrice;
    }
} 