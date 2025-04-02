package org.prototype.demo.hotel.adapter;

import org.prototype.demo.common.service.IExternalService;
import org.prototype.demo.hotel.api.HotelAPI;
import org.prototype.demo.hotel.model.HotelRequest;
import java.util.List;

public class HotelServiceAdapter implements IExternalService {
    private final HotelAPI hotelService;

    public HotelServiceAdapter(HotelAPI hotelService) {
        this.hotelService = hotelService;
    }

    @Override
    public Object executeRequest(Object request) {
        if (request instanceof HotelRequest) {
            HotelRequest hotelRequest = (HotelRequest) request;
            return hotelService.findRooms(
                hotelRequest.getLocation(),
                hotelRequest.getDates()
            );
        }
        throw new IllegalArgumentException("Invalid request type for HotelServiceAdapter");
    }
} 