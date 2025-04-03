package org.prototype.demo.common.Facade;

import java.util.List;
import org.prototype.demo.common.model.TripResult;
import org.prototype.demo.common.model.SearchCriteria;
import org.prototype.demo.common.model.BookingConfirmation;
import org.prototype.demo.common.model.BookingRequest;
import org.prototype.demo.common.model.CancellationResult;

public interface IExternalServiceFacade {
    List<TripResult> searchTrips(SearchCriteria criteria);
    BookingConfirmation bookTrip(BookingRequest request);
    CancellationResult cancelTrip(String bookingId);
} 