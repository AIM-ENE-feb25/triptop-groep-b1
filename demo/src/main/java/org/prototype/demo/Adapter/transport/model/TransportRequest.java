package org.prototype.demo.Adapter.transport.model;

/**
 * Represents a request for transport services
 */
public class TransportRequest {
    private String from;
    private String to;

    public TransportRequest(String from, String to) {
        this.from = from;
        this.to = to;
    }

    // Getters and setters
    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }
    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }
} 