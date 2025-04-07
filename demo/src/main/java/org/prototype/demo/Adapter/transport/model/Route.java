package org.prototype.demo.Adapter.transport.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Route {
    private String from;
    private String to;
    private String transportType;
    private double price;
    private int duration;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
}