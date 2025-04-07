package org.prototype.demo.Adapter.hotel.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;
import org.prototype.demo.Adapter.config.ApiConfig;
import org.prototype.demo.Adapter.hotel.adapter.HotelAdapter;
import org.prototype.demo.Adapter.hotel.model.DateRange;
import org.prototype.demo.Adapter.hotel.model.Room;
import org.prototype.demo.Adapter.hotel.model.TripAdvisorHotelResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class HotelAPI {
    private final AsyncHttpClient asyncHttpClient;
    private final ApiConfig apiConfig;
    private final ObjectMapper objectMapper;
    private final HotelAdapter hotelAdapter;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    public HotelAPI(AsyncHttpClient asyncHttpClient,
            ApiConfig apiConfig,
            ObjectMapper objectMapper,
            HotelAdapter hotelAdapter) {
        this.asyncHttpClient = asyncHttpClient;
        this.apiConfig = apiConfig;
        this.objectMapper = objectMapper;
        this.hotelAdapter = hotelAdapter;
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

            return hotelAdapter.adapt(hotelResponse);
        } catch (Exception e) {
            log.error("Error handling response: ", e);
            throw new RuntimeException("Failed to process response: " + e.getMessage(), e);
        }
    }
}