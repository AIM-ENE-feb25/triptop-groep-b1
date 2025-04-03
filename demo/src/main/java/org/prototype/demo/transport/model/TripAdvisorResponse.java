package org.prototype.demo.transport.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TripAdvisorResponse {
    private boolean status;
    private String message;
    private long timestamp;
    private List<AirportData> data;

    @Data
    public static class AirportData {
        private String name;
        private String type;
        private String airportCode;
        private String shortName;
        private List<AirportData> children;
        private AirportDetails details;
    }

    @Data
    public static class AirportDetails {
        private String parent_name;
        private String grandparent_name;
        private String geo_name;
    }
}