package org.prototype.demo.hotel.adapter;

import org.prototype.demo.common.service.IExternalService;
import org.prototype.demo.hotel.api.HotelAPI;
import org.prototype.demo.hotel.model.HotelRequest;
import org.prototype.demo.hotel.model.Room;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class HotelServiceAdapter implements IExternalService<HotelRequest, List<Room>> {
    private final HotelAPI hotelService;

    public HotelServiceAdapter(HotelAPI hotelService) {
        this.hotelService = hotelService;
    }

    @Override
    public CompletableFuture<List<Room>> executeRequest(HotelRequest request) {
        return hotelService.findRooms(
                request.getLocation(),
                request.getDates());
    }
}