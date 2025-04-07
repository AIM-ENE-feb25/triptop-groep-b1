package org.prototype.demo.Facade.Interfaces;

import org.prototype.demo.Facade.Models.*;

import java.util.List;

/**
 * Facade interface for external trip booking services.
 * This interface provides a simplified interface to a complex subsystem of external services,
 * making it easier to search, book, and cancel trips across multiple providers.
 */
public interface IExternalServiceFacade {
    /**
     * Searches for available trips across all registered external services.
     *
     * @param criteria The search criteria containing destination, dates, etc.
     * @return A combined list of matching trip results from all services
     * @throws IllegalArgumentException if criteria is invalid
     */
    List<TripResult> searchTrips(SearchCriteria criteria);

    /**
     * Books a trip through one of the available external services.
     * The implementation will try each service until a successful booking is made.
     *
     * @param request The booking request containing trip and passenger details
     * @return A booking confirmation with the booking details
     * @throws IllegalArgumentException if request is invalid
     * @throws RuntimeException if booking fails with all available services
     */
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