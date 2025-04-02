package org.prototype.demo.transport.adapter;

import lombok.Data;
import org.prototype.demo.transport.model.Transport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FlightTransportAdapter implements TransportAdapter {

    @Override
    public List<Transport> adapt(Object externalData) {
        if (!(externalData instanceof TripAdvisorResponse)) {
            throw new IllegalArgumentException("Expected TripAdvisorResponse but got " + externalData.getClass());
        }

        TripAdvisorResponse response = (TripAdvisorResponse) externalData;

        if (!response.isStatus()) {
            throw new RuntimeException(response.getMessage());
        }

        if (response.getData() == null || response.getData().isEmpty()) {
            return List.of();
        }

        // Find the London entry that contains all airports
        return response.getData().stream()
                .filter(airport -> airport.getChildren() != null && !airport.getChildren().isEmpty())
                .flatMap(airport -> airport.getChildren().stream())
                .filter(child -> child.getName().startsWith("London, United Kingdom"))
                .map(this::parseAirportInfo)
                .collect(Collectors.toList());
    }

    private Transport parseAirportInfo(AirportData airport) {
        // Example: "London, United Kingdom - Heathrow Airport (LHR)"
        String fullName = airport.getName();
        String[] parts = fullName.split(" - ");
        if (parts.length != 2) {
            return new Transport("", "", fullName);
        }

        String[] locationParts = parts[0].split(", ");
        String city = locationParts[0];
        String country = locationParts.length > 1 ? locationParts[1] : "";
        String airportName = parts[1].replaceAll("\\([^)]*\\)", "").trim();

        return new Transport(city, country, airportName);
    }

    @Data
    public static class TripAdvisorResponse {
        private boolean status;
        private String message;
        private long timestamp;
        private List<AirportData> data;
    }

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