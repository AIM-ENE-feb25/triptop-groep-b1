package org.prototype.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.prototype.demo.hotel.api.HotelAPI;
import org.prototype.demo.hotel.model.DateRange;
import org.prototype.demo.hotel.model.Room;
import org.prototype.demo.transport.api.TransportAPI;
import org.prototype.demo.transport.model.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/api/travel")
public class TravelController {

    private final TransportAPI transportAPI;
    private final HotelAPI hotelAPI;

    @Autowired
    public TravelController(TransportAPI transportAPI, HotelAPI hotelAPI) {
        this.transportAPI = transportAPI;
        this.hotelAPI = hotelAPI;
    }

    @GetMapping("/airports")
    public CompletableFuture<ResponseEntity<List<Route>>> searchAirports(@RequestParam String location) {
        return transportAPI.searchAirports(location)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/hotels/search")
    public CompletableFuture<ResponseEntity<List<Room>>> searchHotels(
            @RequestParam String location,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {
        
        DateRange dates = new DateRange(checkIn, checkOut);
        return hotelAPI.findRooms(location, dates)
            .thenApply(ResponseEntity::ok);
    }
} 