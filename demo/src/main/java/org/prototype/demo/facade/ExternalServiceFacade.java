package org.prototype.demo.facade;

import org.prototype.demo.model.SearchCriteria;
import org.prototype.demo.model.TripResult;
import org.prototype.demo.model.BookingRequest;
import org.prototype.demo.model.BookingConfirmation;
import org.prototype.demo.model.CancellationResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExternalServiceFacade implements IExternalServiceFacade {
    private final List<IExternalService> externalServices;

    public ExternalServiceFacade(List<IExternalService> externalServices) {
        this.externalServices = externalServices;
    }

    @Override
    public List<TripResult> searchTrips(SearchCriteria criteria) {
        validateRequest(criteria);
        
        return externalServices.stream()
            .map(service -> service.execute(criteria))
            .filter(Response::isValid)
            .map(this::mapToTripResult)
            .collect(Collectors.toList());
    }

    @Override
    public BookingConfirmation bookTrip(BookingRequest request) {
        validateRequest(request);
        // Implement booking logic using external services
        return null; // TODO: Implement
    }

    @Override
    public CancellationResult cancelTrip(String bookingId) {
        // Implement cancellation logic using external services
        return null; // TODO: Implement
    }

    private void validateRequest(Request request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
    }

    private TripResult mapToTripResult(Response response) {
        if (response instanceof HotelResponse hotelResponse) {
            return new TripResult(hotelResponse.getRooms());
        } else if (response instanceof TransportResponse transportResponse) {
            return new TripResult(transportResponse.getTransports());
        }
        throw new IllegalArgumentException("Unsupported response type");
    }
} 