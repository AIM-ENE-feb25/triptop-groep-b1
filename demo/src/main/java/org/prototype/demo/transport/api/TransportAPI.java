package org.prototype.demo.transport.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;
import org.prototype.demo.config.ApiConfig;
import org.prototype.demo.transport.model.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class TransportAPI {
    private final AsyncHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final ApiConfig apiConfig;

    @Autowired
    public TransportAPI(
            AsyncHttpClient httpClient,
            ObjectMapper objectMapper,
            ApiConfig apiConfig) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.apiConfig = apiConfig;
    }

    public CompletableFuture<List<Route>> searchAirports(String location) {
        log.info("Searching airports for location: {}", location);

        // Correct endpoint based on RapidAPI documentation
        String url = String.format("https://%s/api/v1/flights/searchAirport?query=%s", apiConfig.getApiHost(),
                location);
        log.info("Making API request to: {}", url);

        return httpClient.prepareGet(url)
                .setHeader("x-rapidapi-key", apiConfig.getApiKey())
                .setHeader("x-rapidapi-host", apiConfig.getApiHost())
                .execute()
                .toCompletableFuture()
                .thenApply(this::handleResponse)
                .exceptionally(ex -> {
                    log.error("Error searching airports: ", ex);
                    return new ArrayList<>(); // Return empty list on error
                });
    }

    private List<Route> handleResponse(Response response) {
        try {
            log.info("Response status: {}", response.getStatusCode());
            String responseBody = response.getResponseBody();
            log.info("Response size: {} bytes", responseBody.length());

            // Check for API error
            if (responseBody.contains("error") || responseBody.contains("does not exist")) {
                log.error("Error response from API: {}", responseBody);
                return new ArrayList<>(); // Return empty list on error
            }

            List<Route> routes = new ArrayList<>();

            // Only proceed with parsing if we have content
            if (responseBody != null && !responseBody.isEmpty()) {
                JsonNode root = objectMapper.readTree(responseBody);

                if (root.has("data") && root.get("data").isArray()) {
                    JsonNode dataArray = root.get("data");

                    for (JsonNode location : dataArray) {
                        // Process main airport (like "London - All Airports")
                        if (location.has("airportCode") && location.has("name")) {
                            Route mainRoute = new Route();
                            mainRoute.setFrom(location.path("airportCode").asText());
                            mainRoute.setTo(location.path("name").asText());
                            mainRoute.setTransportType("AIRPORT");
                            mainRoute.setPrice(0.0);
                            mainRoute.setDuration(0);
                            routes.add(mainRoute);
                        }

                        // Process children (specific airports in the location)
                        if (location.has("children") && location.get("children").isArray()) {
                            JsonNode children = location.get("children");
                            for (JsonNode airport : children) {
                                if (airport.has("airportCode") && airport.has("name")) {
                                    Route childRoute = new Route();
                                    childRoute.setFrom(airport.path("airportCode").asText());
                                    childRoute.setTo(airport.path("name").asText());
                                    childRoute.setTransportType("AIRPORT");
                                    childRoute.setPrice(0.0);
                                    childRoute.setDuration(0);
                                    routes.add(childRoute);
                                }
                            }
                        }
                    }
                }
            }

            return routes;
        } catch (Exception e) {
            log.error("Error parsing response: ", e);
            return new ArrayList<>(); // Return empty list on error
        }
    }
}