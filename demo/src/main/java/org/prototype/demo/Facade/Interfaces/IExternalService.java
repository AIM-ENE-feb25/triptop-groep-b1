package org.prototype.demo.Facade.Interfaces;

import org.prototype.demo.Facade.Models.*;

import java.util.List;


public interface IExternalService {
    List<TripResult> searchTrips(SearchCriteria criteria);

    BookingConfirmation bookTrip(BookingRequest request);

    CancellationResult cancelTrip(String bookingId);
}