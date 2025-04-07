package org.prototype.demo.Facade;

import org.prototype.demo.Facade.Interfaces.IExternalService;
import org.prototype.demo.Facade.Interfaces.IExternalServiceFacade;
import org.prototype.demo.Facade.Models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExternalServiceFacade implements IExternalServiceFacade {
    private final List<IExternalService> externalServices;

    public ExternalServiceFacade(List<IExternalService> externalServices) {
        if (externalServices == null || externalServices.isEmpty()) {
            throw new IllegalArgumentException("External services list cannot be null or empty");
        }
        this.externalServices = new ArrayList<>(externalServices);
    }

    @Override
    public List<TripResult> searchTrips(SearchCriteria criteria) {
        validateSearchCriteria(criteria);
        
        return externalServices.stream()
                .flatMap(service -> service.searchTrips(criteria).stream())
                .collect(Collectors.toList());
    }

    @Override
    public BookingConfirmation bookTrip(BookingRequest request) {
        validateBookingRequest(request);
        
        for (IExternalService service : externalServices) {
            try {
                return service.bookTrip(request);
            } catch (Exception e) {
                // Log the error and try the next service
                System.err.println("Failed to book with service: " + e.getMessage());
            }
        }
        throw new RuntimeException("Failed to book trip with any available service");
    }

    @Override
    public CancellationResult cancelTrip(String bookingId) {
        if (bookingId == null || bookingId.trim().isEmpty()) {
            throw new IllegalArgumentException("Booking ID cannot be null or empty");
        }

        for (IExternalService service : externalServices) {
            try {
                return service.cancelTrip(bookingId);
            } catch (Exception e) {
                // Log the error and try the next service
                System.err.println("Failed to cancel with service: " + e.getMessage());
            }
        }
        throw new RuntimeException("Failed to cancel trip with any available service");
    }

    private void validateSearchCriteria(SearchCriteria criteria) {
        if (criteria == null) {
            throw new IllegalArgumentException("Search criteria cannot be null");
        }
        if (criteria.getDestination() == null || criteria.getDestination().trim().isEmpty()) {
            throw new IllegalArgumentException("Destination cannot be null or empty");
        }
        if (criteria.getStartDate() == null) {
            throw new IllegalArgumentException("Start date cannot be null");
        }
    }

    private void validateBookingRequest(BookingRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Booking request cannot be null");
        }
        if (request.getTripId() == null || request.getTripId().trim().isEmpty()) {
            throw new IllegalArgumentException("Trip ID cannot be null or empty");
        }
        if (request.getPassengerCount() <= 0) {
            throw new IllegalArgumentException("Passenger count must be greater than 0");
        }
    }
}