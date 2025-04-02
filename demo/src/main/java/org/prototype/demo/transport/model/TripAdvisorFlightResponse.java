package org.prototype.demo.transport.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TripAdvisorFlightResponse {
    @JsonProperty("data")
    private List<AirportData> data;

    @Data
    public static class AirportData {
        @JsonProperty("airportCode")
        private String airportCode;
        
        @JsonProperty("name")
        private String name;
        
        @JsonProperty("cityName")
        private String cityName;
        
        @JsonProperty("countryName")
        private String countryName;
    }
} 