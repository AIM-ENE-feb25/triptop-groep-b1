package org.prototype.demo.Facade.Services;

import org.prototype.demo.Facade.Interfaces.IExternalService;
import org.prototype.demo.Facade.Models.LocationSearchCriteria;
import org.prototype.demo.Facade.Models.LocationSearchResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class BookingService implements IExternalService {
    private static final String API_URL = "https://booking-com15.p.rapidapi.com/api/v1/cars/searchDestination";
    private static final String API_KEY = "41f5c22de2mshb11a6744daaf9cbp1bb8dejsnadf9013ccbb2";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public BookingService() {
        // API key is already set
    }

    @Override
    public void setApiKey(String apiKey) {
        // API key is already set, this method is kept for interface compliance
    }

    @Override
    public List<LocationSearchResult> searchLocations(LocationSearchCriteria criteria) {
        try {
            String url = API_URL + "?query=" + criteria.getLocation() + "&languagecode=" + criteria.getLanguageCode();
            System.out.println("Booking.com API URL: " + url);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("X-RapidAPI-Key", API_KEY)
                    .header("X-RapidAPI-Host", "booking-com15.p.rapidapi.com")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Booking.com API Response Status: " + response.statusCode());
            System.out.println("Booking.com API Response Body: " + response.body());
            
            // Parse the JSON response
            JsonNode rootNode = objectMapper.readTree(response.body());
            JsonNode dataNode = rootNode.get("data");
            
            List<LocationSearchResult> results = new ArrayList<>();
            if (dataNode != null && dataNode.isArray()) {
                for (JsonNode locationNode : dataNode) {
                    String locationId = locationNode.get("location_id") != null ? 
                            locationNode.get("location_id").asText() : 
                            locationNode.get("iata_code") != null ? 
                            locationNode.get("iata_code").asText() : 
                            "N/A";
                    
                    String locationName = locationNode.get("name").asText();
                    results.add(new LocationSearchResult(locationId, locationName, "Booking.com"));
                }
            }
            
            System.out.println("Booking.com Results Count: " + results.size());
            return results;
        } catch (Exception e) {
            System.err.println("Booking.com API Error: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to search locations with Booking.com", e);
        }
    }
} 