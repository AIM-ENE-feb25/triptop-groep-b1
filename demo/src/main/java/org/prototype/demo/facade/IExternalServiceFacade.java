package org.prototype.demo.facade;

import org.prototype.demo.model.SearchCriteria;
import org.prototype.demo.model.TripResult;
import org.prototype.demo.model.BookingRequest;
import org.prototype.demo.model.BookingConfirmation;
import org.prototype.demo.model.CancellationResult;

import java.util.List;

public interface IExternalServiceFacade {
    List<TripResult> searchTrips(SearchCriteria criteria);
    BookingConfirmation bookTrip(BookingRequest request);
    CancellationResult cancelTrip(String bookingId);
} 