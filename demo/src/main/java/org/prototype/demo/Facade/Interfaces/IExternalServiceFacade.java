package org.prototype.demo.Facade.Interfaces;

import java.util.List;

public interface IExternalServiceFacade {
    List<TripResult> searchTrips(SearchCriteria criteria);
    BookingConfirmation bookTrip(BookingRequest request);
    CancellationResult cancelTrip(String bookingId);
}