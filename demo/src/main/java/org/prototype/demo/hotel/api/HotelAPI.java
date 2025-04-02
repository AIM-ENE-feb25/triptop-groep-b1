package org.prototype.demo.hotel.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;
import org.prototype.demo.config.ApiConfig;
import org.prototype.demo.hotel.model.DateRange;
import org.prototype.demo.hotel.model.Room;
import org.prototype.demo.hotel.model.TripAdvisorHotelResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HotelAPI {
    private final AsyncHttpClient asyncHttpClient;
    private final ApiConfig apiConfig;
    private final ObjectMapper objectMapper;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    public HotelAPI(AsyncHttpClient asyncHttpClient, ApiConfig apiConfig, ObjectMapper objectMapper) {
        this.asyncHttpClient = asyncHttpClient;
        this.apiConfig = apiConfig;
        this.objectMapper = objectMapper;
    }

    public CompletableFuture<List<Room>> findRooms(String geoId, DateRange dates) {
        String url = String.format("https://%s/api/v1/hotels/searchHotels?geoId=%s&checkIn=%s&checkOut=%s",
                apiConfig.getApiHost(),
                geoId,
                dates.getCheckIn().format(DATE_FORMATTER),
                dates.getCheckOut().format(DATE_FORMATTER));

        log.info("Making hotel search request to URL: {}", url);

        return asyncHttpClient.prepareGet(url)
                .setHeader("x-rapidapi-key", apiConfig.getApiKey())
                .setHeader("x-rapidapi-host", apiConfig.getApiHost())
                .execute()
                .toCompletableFuture()
                .thenApply(this::handleResponse);
    }

    private List<Room> handleResponse(Response response) {
        try {
            String responseBody = response.getResponseBody();
            log.info("Received response with status {} and body: {}", response.getStatusCode(), responseBody);

            if (response.getStatusCode() != 200) {
                log.error("API request failed with status {}: {}", response.getStatusCode(), responseBody);
                throw new RuntimeException("API request failed with status " + response.getStatusCode());
            }

            TripAdvisorHotelResponse hotelResponse = objectMapper.readValue(
                    responseBody,
                    TripAdvisorHotelResponse.class);

            if (!hotelResponse.isStatus()) {
                String errorMessage = formatErrorMessage(hotelResponse.getMessage());
                log.error("API returned error: {}", errorMessage);
                throw new RuntimeException(errorMessage);
            }

            if (hotelResponse.getData() == null || hotelResponse.getData().getData() == null
                    || hotelResponse.getData().getData().isEmpty()) {
                log.warn("No hotel data found in response");
                return List.of();
            }

            return hotelResponse.getData().getData().stream()
                    .map(hotelData -> new Room(
                            hotelData.getTitle(),
                            hotelData.getCommerceInfo().getPriceForDisplay().getText(),
                            hotelData.getBubbleRating().getRating(),
                            hotelData.getSecondaryInfo()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error handling response: ", e);
            throw new RuntimeException("Failed to process response: " + e.getMessage(), e);
        }
    }

    private String formatErrorMessage(Object message) {
        if (message instanceof List) {
            List<?> errors = (List<?>) message;
            return errors.stream()
                    .map(error -> error.toString())
                    .collect(Collectors.joining(", "));
        }
        return message != null ? message.toString() : "Unknown error";
    }
}