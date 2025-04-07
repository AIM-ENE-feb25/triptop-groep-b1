package org.prototype.demo.Facade.Interfaces;

import org.prototype.demo.Facade.Models.*;

import java.util.List;


public interface IExternalServiceFacade {

    List<TripResult> searchTrips(SearchCriteria criteria);


    BookingConfirmation bookTrip(BookingRequest request);

    /**
     * Cancels a previously booked trip.
     * The implementation will try each service until the booking is found and cancelled.
     *
     * @param bookingId The unique identifier of the booking to cancel
     * @return The result of the cancellation operation
     * @throws IllegalArgumentException if bookingId is invalid
     * @throws RuntimeException if cancellation fails with all available services
     */
    CancellationResult cancelTrip(String bookingId);
}