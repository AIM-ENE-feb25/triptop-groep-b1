package org.prototype.demo.transport.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;
import org.prototype.demo.common.model.ApiResponse;
import org.prototype.demo.common.model.SearchCriteria;
import org.prototype.demo.common.service.IExternalService;
import org.prototype.demo.config.ApiConfig;
import org.prototype.demo.transport.model.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class TransportAPI implements IExternalService {
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

    @Override
    public CompletableFuture<ApiResponse> executeRequest(Object request) {
        if (!(request instanceof SearchCriteria)) {
            throw new IllegalArgumentException("Request must be of type SearchCriteria");
        }

        SearchCriteria criteria = (SearchCriteria) request;
        String url = String.format("https://%s/airports/search?query=%s", apiConfig.getApiHost(), criteria.getDestination());
        
        log.info("Making transport API request to: {}", url);
        
        return httpClient.prepareGet(url)
                .setHeader("X-RapidAPI-Key", apiConfig.getApiKey())
                .setHeader("X-RapidAPI-Host", apiConfig.getApiHost())
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
            log.info("Received transport API response with status: {}", response.getStatusCode());
            
            if (response.getStatusCode() != 200) {
                return new ApiResponse(false, "API request failed with status: " + response.getStatusCode(), null);
            }

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
            
            log.info("Successfully processed {} routes", routes.size());
            return new ApiResponse(true, "Success", routes);
        } catch (Exception e) {
            log.error("Error handling transport API response: ", e);
            return new ApiResponse(false, "Failed to parse airport search response: " + e.getMessage(), null);
        }
    }
} 