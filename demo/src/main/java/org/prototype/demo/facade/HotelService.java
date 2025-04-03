package org.prototype.demo.facade;

import org.prototype.demo.hotel.api.HotelAPI;
import org.prototype.demo.hotel.model.HotelRequest;
import org.prototype.demo.hotel.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class HotelService implements IExternalService {
    private final HotelAPI hotelAPI;

    @Autowired
    public HotelService(HotelAPI hotelAPI) {
        this.hotelAPI = hotelAPI;
    }

    @Override
    public Response execute(Request request) {
        if (request instanceof HotelRequest hotelRequest) {
            CompletableFuture<List<Room>> roomsFuture = hotelAPI.findRooms(
                hotelRequest.getGeoId(),
                hotelRequest.getDateRange()
            );
            
            // Convert the CompletableFuture to a Response
            return new HotelResponse(roomsFuture.join());
        }
        throw new IllegalArgumentException("Invalid request type for HotelService");
    }

    @Override
    public boolean validate(Response response) {
        return response instanceof HotelResponse;
    }
} 