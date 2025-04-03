package org.prototype.demo.facade;

import org.prototype.demo.transport.model.Transport;

import java.util.List;

public class TransportResponse extends Response {
    private final List<Transport> transports;

    public TransportResponse(List<Transport> transports) {
        this.transports = transports;
    }

    public List<Transport> getTransports() {
        return transports;
    }
} 