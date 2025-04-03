package org.prototype.demo.model;

import org.prototype.demo.hotel.model.Room;
import org.prototype.demo.transport.model.Transport;

import java.util.List;

public class TripResult {
    private final List<Room> rooms;
    private final List<Transport> transports;

    public TripResult(List<Room> rooms) {
        this.rooms = rooms;
        this.transports = List.of();
    }

    public TripResult(List<Transport> transports) {
        this.rooms = List.of();
        this.transports = transports;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Transport> getTransports() {
        return transports;
    }
} 