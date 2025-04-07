package org.prototype.demo.Adapter.transport.adapter;

import org.prototype.demo.Adapter.transport.model.Transport;
import org.prototype.demo.Adapter.transport.model.TripAdvisorResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

@Component
public class FlightTransportAdapter implements TransportAdapter {

    @Override
    public List<Transport> adapt(TripAdvisorResponse response) {
        if (!response.isStatus()) {
            throw new RuntimeException(response.getMessage());
        }

        if (response.getData() == null || response.getData().isEmpty()) {
            return List.of();
        }

        List<Transport> allAirports = new ArrayList<>();

        // Process each airport entry
        for (TripAdvisorResponse.AirportData airport : response.getData()) {
            // Add the main airport
            allAirports.add(parseAirportInfo(airport));

            // Add all child airports if they exist
            if (airport.getChildren() != null) {
                allAirports.addAll(
                        airport.getChildren().stream()
                                .map(this::parseAirportInfo)
                                .collect(Collectors.toList()));
            }
        }

        return allAirports;
    }

    private Transport parseAirportInfo(TripAdvisorResponse.AirportData airport) {
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
}