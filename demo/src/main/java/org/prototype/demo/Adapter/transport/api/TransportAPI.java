package org.prototype.demo.Adapter.transport.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;
import org.prototype.demo.Adapter.config.ApiConfig;
import org.prototype.demo.Adapter.transport.adapter.TransportAdapter;
import org.prototype.demo.Adapter.transport.model.Transport;
import org.prototype.demo.Adapter.transport.model.TripAdvisorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class TransportAPI {
    private final AsyncHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final ApiConfig apiConfig;
    private final TransportAdapter transportAdapter;

    @Autowired
    public TransportAPI(
            AsyncHttpClient httpClient,
            ObjectMapper objectMapper,
            ApiConfig apiConfig,
            TransportAdapter transportAdapter) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.apiConfig = apiConfig;
        this.transportAdapter = transportAdapter;
    }

    public CompletableFuture<List<Transport>> searchAirports(String location) {
        log.info("Searching airports for location: {}", location);

        String url = String.format("https://%s/api/v1/flights/searchAirport?query=%s",
                apiConfig.getApiHost(), location);
        log.info("Making API request to: {}", url);

        return httpClient.prepareGet(url)
                .setHeader("x-rapidapi-key", apiConfig.getApiKey())
                .setHeader("x-rapidapi-host", apiConfig.getApiHost())
                .execute()
                .toCompletableFuture()
                .thenApply(this::handleResponse)
                .exceptionally(ex -> {
                    log.error("Error searching airports: ", ex);
                    return List.of();
                });
    }

    private List<Transport> handleResponse(Response response) {
        try {
            log.info("Response status: {}", response.getStatusCode());
            String responseBody = response.getResponseBody();
            log.info("Response size: {} bytes", responseBody.length());

            if (response.getStatusCode() != 200) {
                log.error("API request failed with status {}: {}", response.getStatusCode(), responseBody);
                return List.of();
            }

            TripAdvisorResponse tripAdvisorResponse = objectMapper.readValue(responseBody,
                    TripAdvisorResponse.class);

            return transportAdapter.adapt(tripAdvisorResponse);
        } catch (Exception e) {
            log.error("Error handling response: ", e);
            return List.of();
        }
    }
}