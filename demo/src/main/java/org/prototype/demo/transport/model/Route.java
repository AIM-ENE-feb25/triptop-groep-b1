package org.prototype.demo.transport.model;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * Represents a transport route between two locations
 */
@Data
public class Route {
    private String from;
    private String to;
    private String transportType;
    private double price;
    private int duration;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    public Route() {
    }

    public Route(String from, String to, String transportType, double price, int duration) {
        this.from = from;
        this.to = to;
        this.transportType = transportType;
        this.price = price;
        this.duration = duration;
    }

    // Getters and setters
    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }
    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }
    public String getTransportType() { return transportType; }
    public void setTransportType(String transportType) { this.transportType = transportType; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
} 