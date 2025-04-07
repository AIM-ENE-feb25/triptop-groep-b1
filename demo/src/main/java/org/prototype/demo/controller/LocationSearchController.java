package org.prototype.demo.controller;

import org.prototype.demo.Facade.Interfaces.IExternalServiceFacade;
import org.prototype.demo.Facade.Models.LocationSearchCriteria;
import org.prototype.demo.Facade.Models.LocationSearchResult;
import org.prototype.demo.Facade.Services.BookingService;
import org.prototype.demo.Facade.Services.TripadvisorService;
import org.prototype.demo.Facade.ExternalServiceFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationSearchController {
    private final IExternalServiceFacade facade;

    public LocationSearchController() {
        // Initialize services and facade
        BookingService bookingService = new BookingService();
        TripadvisorService tripadvisorService = new TripadvisorService();
        this.facade = new ExternalServiceFacade(List.of(bookingService, tripadvisorService));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchLocations(
            @RequestParam String location,
            @RequestParam(defaultValue = "en-gb") String languageCode) {
        
        try {
            System.out.println("Searching locations for: " + location + " with language: " + languageCode);
            
            LocationSearchCriteria criteria = new LocationSearchCriteria(location, languageCode);
            List<LocationSearchResult> results = facade.searchLocations(criteria);
            
            System.out.println("Total results found: " + results.size());
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            System.err.println("Error searching locations: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error searching locations: " + e.getMessage());
        }
    }
} 