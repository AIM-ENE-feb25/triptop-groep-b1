package org.prototype.demo.Facade;

import org.prototype.demo.Facade.Interfaces.IExternalService;
import org.prototype.demo.Facade.Interfaces.IExternalServiceFacade;

import java.util.List;


public class ExternalServiceFacade implements IExternalServiceFacade {
    private List<IExternalService> externalServices;

    public ExternalServiceFacade(List<IExternalService> externalServices) {
        this.externalServices = externalServices;
    }

    @Override
    public List<TripResult> searchTrips(SearchCriteria criteria) {
        // Implementation here
        return null;
    }

    @Override
    public BookingConfirmation bookTrip(BookingRequest request) {
        // Implementation here
        return null;
    }

    @Override
    public CancellationResult cancelTrip(String bookingId) {
        // Implementation here
        return null;
    }

    private void validateRequest(Request request) {
        // Implementation here
    }
}