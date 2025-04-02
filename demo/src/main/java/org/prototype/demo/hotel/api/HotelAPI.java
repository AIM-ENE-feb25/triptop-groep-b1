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

    public CompletableFuture<List<Room>> findRooms(String location, DateRange dates) {
        String url = String.format("https://%s/api/v1/hotels/searchHotels?geoId=%s&checkIn=%s&checkOut=%s",
            apiConfig.getApiHost(),
            location,
            dates.getCheckIn().format(DATE_FORMATTER),
            dates.getCheckOut().format(DATE_FORMATTER));

        return asyncHttpClient.prepareGet(url)
            .setHeader("x-rapidapi-key", apiConfig.getApiKey())
            .setHeader("x-rapidapi-host", apiConfig.getApiHost())
            .execute()
            .toCompletableFuture()
            .thenApply(this::handleResponse);
    }

    private List<Room> handleResponse(Response response) {
        try {
            TripAdvisorHotelResponse hotelResponse = objectMapper.readValue(
                response.getResponseBodyAsStream(),
                TripAdvisorHotelResponse.class
            );

            return hotelResponse.getData().stream()
                .map(hotelData -> new Room(
                    hotelData.getName(),
                    hotelData.getPrice().getAmount(),
                    hotelData.getRating().getCount(),
                    String.format("%s, %s, %s", 
                        hotelData.getLocation().getAddress(),
                        hotelData.getLocation().getCity(),
                        hotelData.getLocation().getCountry())
                ))
                .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error handling response: ", e);
            throw new RuntimeException("Failed to process response", e);
        }
    }
} 