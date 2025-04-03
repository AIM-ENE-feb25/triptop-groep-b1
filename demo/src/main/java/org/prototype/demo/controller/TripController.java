package org.prototype.demo.controller;

import org.prototype.demo.facade.IExternalServiceFacade;
import org.prototype.demo.model.SearchCriteria;
import org.prototype.demo.model.TripResult;
import org.prototype.demo.hotel.model.DateRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {
    private final IExternalServiceFacade tripFacade;

    @Autowired
    public TripController(IExternalServiceFacade tripFacade) {
        this.tripFacade = tripFacade;
    }

    @GetMapping("/search")
    public List<TripResult> searchTrips(
            @RequestParam String location,
            @RequestParam String checkIn,
            @RequestParam String checkOut) {
        
        DateRange dateRange = new DateRange(
            LocalDate.parse(checkIn),
            LocalDate.parse(checkOut)
        );
        
        SearchCriteria criteria = new SearchCriteria(location, dateRange);
        return tripFacade.searchTrips(criteria);
    }
} 