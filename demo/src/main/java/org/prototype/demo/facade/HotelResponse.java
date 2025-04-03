package org.prototype.demo.facade;

import org.prototype.demo.hotel.model.Room;

import java.util.List;

public class HotelResponse extends Response {
    private final List<Room> rooms;

    public HotelResponse(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Room> getRooms() {
        return rooms;
    }
} 