package org.prototype.demo.Facade.Interfaces;

import org.prototype.demo.Facade.Models.*;

import java.util.List;

/**
 * Interface defining the contract for external trip booking services.
 * Implementations of this interface should provide concrete implementations
 * for searching, booking, and canceling trips through specific external services.
 */
public interface IExternalService {
    /**
     * Searches for available trips based on the given criteria.
     *
     * @param criteria The search criteria containing destination, dates, etc.
     * @return A list of matching trip results
     * @throws IllegalArgumentException if criteria is invalid
     */
    List<TripResult> searchTrips(SearchCriteria criteria);

    /**
     * Books a trip based on the provided request.
     *
     * @param request The booking request containing trip and passenger details
     * @return A booking confirmation with the booking details
     * @throws IllegalArgumentException if request is invalid
     * @throws RuntimeException if booking fails
     */
    BookingConfirmation bookTrip(BookingRequest request);

    /**
     * Cancels a previously booked trip.
     *
     * @param bookingId The unique identifier of the booking to cancel
     * @return The result of the cancellation operation
     * @throws IllegalArgumentException if bookingId is invalid
     * @throws RuntimeException if cancellation fails
     */
    CancellationResult cancelTrip(String bookingId);
}