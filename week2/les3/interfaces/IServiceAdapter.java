// package com.triptop.external;

// import java.util.concurrent.CompletableFuture;

// public interface IExternalServiceGateway {
//     CompletableFuture<ServiceResponse> sendRequest(ServiceRequest request);
//     void validateResponse(ServiceResponse response) throws ValidationException;
//     void handleError(ServiceError error);
//     void retryRequest(ServiceRequest request, int attempts);
// }

// public interface IServiceAdapter {
//     CompletableFuture<SearchResult> search(SearchCriteria criteria);
//     CompletableFuture<BookingResult> book(BookingRequest request);
//     CompletableFuture<CancellationResult> cancel(String bookingId);
// }

// public interface IExternalServiceOrchestrator {
//     CompletableFuture<List<SearchResult>> searchAllServices(SearchCriteria criteria);
//     CompletableFuture<BookingConfirmation> createBooking(BookingRequest request);
//     CompletableFuture<Void> cancelBooking(String bookingId);
// }

// public interface ISecurityManager {
//     Credentials getCredentials(String serviceName);
//     void validateCredentials(Credentials credentials);
//     void rotateCredentials(String serviceName);
//  } 