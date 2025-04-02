package org.prototype.demo.transport.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;
import org.prototype.demo.common.client.ServiceClient;
import org.prototype.demo.common.service.IExternalService;
import org.prototype.demo.transport.model.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TransportAPI implements IExternalService {
    private final AsyncHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String apiKey;
    private final String apiHost;

    @Autowired
    public TransportAPI(AsyncHttpClient httpClient, ObjectMapper objectMapper, String apiKey, String apiHost) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.apiKey = apiKey;
        this.apiHost = apiHost;
    }

    public CompletableFuture<List<Route>> searchAirports(String location) {
        String url = String.format("https://%s/airports/search?query=%s", apiHost, location);
        
        return httpClient.prepareGet(url)
                .setHeader("X-RapidAPI-Key", apiKey)
                .setHeader("X-RapidAPI-Host", apiHost)
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
                        route.setPrice(0.0);
                        route.setDepartureTime(null);
                        route.setArrivalTime(null);
                        routes.add(route);
                    }
                }
            }
            
            return routes;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse airport search response", e);
        }
    }

    @Override
    public Object executeRequest(Object request) {
        if (request instanceof String) {
            return searchAirports((String) request);
        }
        throw new IllegalArgumentException("Request must be a String containing the location to search for");
    }
} 