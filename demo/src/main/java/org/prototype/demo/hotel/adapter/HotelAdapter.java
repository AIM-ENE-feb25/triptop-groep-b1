package org.prototype.demo.hotel.adapter;

import org.prototype.demo.hotel.model.Room;
import org.prototype.demo.hotel.model.TripAdvisorHotelResponse;
import java.util.List;

public interface HotelAdapter {
    /**
     * Adapts external hotel data into our domain model
     * 
     * @param externalData The raw data from the external API
     * @return List of Room objects in our domain model
     */
    List<Room> adapt(TripAdvisorHotelResponse externalData);
}