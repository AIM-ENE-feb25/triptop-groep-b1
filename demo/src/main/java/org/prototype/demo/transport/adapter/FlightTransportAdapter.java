package org.prototype.demo.transport.adapter;

import lombok.Data;
import org.prototype.demo.transport.model.Transport;
import org.prototype.demo.transport.model.TripAdvisorResponse;
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

        // Process all airports in the data array
        return response.getData().stream()
                .map(this::parseAirportInfo)
                .collect(Collectors.toList());
    }

    private Transport parseAirportInfo(TripAdvisorResponse.AirportData airport) {
        // Example: "Amsterdam, Netherlands - Amsterdam-Schiphol Airport (AMS)"
        String fullName = airport.getName();
        String[] parts = fullName.split(" - ");
        if (parts.length != 2) {
            return new Transport("", "", fullName);
        }

        String[] locationParts = parts[0].split(", ");
        String city = locationParts[0];
        String country = locationParts.length > 1 ? locationParts[1] : "";
        String airportName = parts[1].replaceAll("\\([^)]*\\)", "").trim(); // Remove airport code in parentheses

        return new Transport(city, country, airportName);
    }
}