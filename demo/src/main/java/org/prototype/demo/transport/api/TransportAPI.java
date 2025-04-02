package org.prototype.demo.transport.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;
import org.prototype.demo.config.ApiConfig;
import org.prototype.demo.transport.model.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
        String url = String.format("https://%s/airports/search?query=%s", apiConfig.getApiHost(), location);
        
        return httpClient.prepareGet(url)
                .setHeader("X-RapidAPI-Key", apiConfig.getApiKey())
                .setHeader("X-RapidAPI-Host", apiConfig.getApiHost())
                .execute()
                .toCompletableFuture()
                .thenApply(this::handleResponse);
    }

    private List<Route> handleResponse(Response response) {
        try {
            JsonNode root = objectMapper.readTree(response.getResponseBody());
            List<Route> routes = new ArrayList<>();
            
            if (root.has("data")) {
                JsonNode data = root.get("data");
                if (data.isArray()) {
                    for (JsonNode airport : data) {
                        Route route = new Route();
                        route.setFrom(airport.path("name").asText());
                        route.setTo(airport.path("city").asText());
                        route.setTransportType("AIRPORT");
                        route.setPrice(0.0);
                        route.setDuration(0);
                        routes.add(route);
                    }
                }
            }
            
            return routes;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse airport search response", e);
        }
    }
} 