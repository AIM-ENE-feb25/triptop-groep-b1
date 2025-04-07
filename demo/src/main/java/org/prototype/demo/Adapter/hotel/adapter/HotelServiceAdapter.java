package org.prototype.demo.Adapter.hotel.adapter;

import org.prototype.demo.Adapter.common.service.IExternalService;
import org.prototype.demo.Adapter.hotel.api.HotelAPI;
import org.prototype.demo.Adapter.hotel.model.HotelRequest;
import org.prototype.demo.Adapter.hotel.model.Room;
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