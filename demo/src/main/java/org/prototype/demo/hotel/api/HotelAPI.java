package org.prototype.demo.hotel.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;
import org.prototype.demo.common.model.ApiResponse;
import org.prototype.demo.common.model.SearchCriteria;
import org.prototype.demo.common.service.IExternalService;
import org.prototype.demo.config.ApiConfig;
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
public class HotelAPI implements IExternalService {
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

    @Override
    public CompletableFuture<ApiResponse> executeRequest(Object request) {
        if (!(request instanceof SearchCriteria)) {
            throw new IllegalArgumentException("Request must be of type SearchCriteria");
        }

        SearchCriteria criteria = (SearchCriteria) request;
        String url = String.format("https://%s/api/v1/hotels/searchHotels?geoId=%s&checkIn=%s&checkOut=%s",
            apiConfig.getApiHost(),
            criteria.getDestination(),
            criteria.getStartDate(),
            criteria.getEndDate());

        log.info("Making hotel API request to: {}", url);

        return asyncHttpClient.prepareGet(url)
            .setHeader("x-rapidapi-key", apiConfig.getApiKey())
            .setHeader("x-rapidapi-host", apiConfig.getApiHost())
            .execute()
            .toCompletableFuture()
            .thenApply(this::handleResponse);
    }

    @Override
    public boolean validate(ApiResponse response) {
        return response != null && response.isSuccess();
    }

    private ApiResponse handleResponse(Response response) {
        try {
            log.info("Received hotel API response with status: {}", response.getStatusCode());
            
            if (response.getStatusCode() != 200) {
                return new ApiResponse(false, "API request failed with status: " + response.getStatusCode(), null);
            }

            TripAdvisorHotelResponse hotelResponse = objectMapper.readValue(
                response.getResponseBodyAsStream(),
                TripAdvisorHotelResponse.class
            );

            List<Room> rooms = hotelResponse.getData().stream()
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

            log.info("Successfully processed {} rooms", rooms.size());
            return new ApiResponse(true, "Success", rooms);
        } catch (Exception e) {
            log.error("Error handling hotel API response: ", e);
            return new ApiResponse(false, "Failed to process response: " + e.getMessage(), null);
        }
    }
} 