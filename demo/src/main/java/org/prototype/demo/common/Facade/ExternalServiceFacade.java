package org.prototype.demo.common.Facade;

import org.prototype.demo.common.service.IExternalService;
import org.prototype.demo.hotel.api.HotelAPI;
import org.prototype.demo.transport.api.TransportAPI;
import org.prototype.demo.common.model.*;
import org.prototype.demo.common.exception.FacadeException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ExternalServiceFacade implements IExternalServiceFacade {
    private List<IExternalService> externalServices;

    public ExternalServiceFacade(List<IExternalService> externalServices) {
        this.externalServices = externalServices;
    }

    @Override
    public List<TripResult> searchTrips(SearchCriteria criteria) {
        validateRequest(criteria);
        List<TripResult> results = new ArrayList<>();
        
        for (IExternalService service : externalServices) {
            if (service instanceof HotelAPI) {
                CompletableFuture<Response> hotelResponse = service.executeRequest(criteria);
                hotelResponse.thenAccept(response -> {
                    if (service.validate(response)) {
                        results.addAll(mapHotelToTripResults(response));
                    }
                });
            } else if (service instanceof TransportAPI) {
                CompletableFuture<Response> transportResponse = service.executeRequest(criteria);
                transportResponse.thenAccept(response -> {
                    if (service.validate(response)) {
                        results.addAll(mapTransportToTripResults(response));
                    }
                });
            }
        }
        
        return results;
    }

    @Override
    public BookingConfirmation bookTrip(BookingRequest request) {
        validateRequest(request);
        throw new FacadeException("Booking functionality not implemented yet");
    }

    @Override
    public CancellationResult cancelTrip(String bookingId) {
        validateRequest(bookingId);
        throw new FacadeException("Cancellation functionality not implemented yet");
    }

    private void validateRequest(Object request) {
        if (request == null) {
            throw new FacadeException("Request cannot be null");
        }
    }

    @SuppressWarnings("unchecked")
    private List<TripResult> mapHotelToTripResults(Response response) {
        List<TripResult> results = new ArrayList<>();
        if (response.getData() instanceof List) {
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) response.getData();
            for (Map<String, Object> data : dataList) {
                results.add(new TripResult(
                    (String) data.get("name"),
                    (String) data.get("location"),
                    null, // startDate not available from hotel API
                    null, // endDate not available from hotel API
                    (Double) data.get("price"),
                    (String) data.get("description")
                ));
            }
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    private List<TripResult> mapTransportToTripResults(Response response) {
        List<TripResult> results = new ArrayList<>();
        if (response.getData() instanceof List) {
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) response.getData();
            for (Map<String, Object> data : dataList) {
                results.add(new TripResult(
                    (String) data.get("from"),
                    (String) data.get("to"),
                    null, // startDate not available from transport API
                    null, // endDate not available from transport API
                    (Double) data.get("price"),
                    "Transport route: " + data.get("from") + " to " + data.get("to")
                ));
            }
        }
        return results;
    }
}
