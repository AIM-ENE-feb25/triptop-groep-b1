package org.prototype.demo.Facade.Models;

public class LocationSearchResult {
    private final String locationId;
    private final String locationName;
    private final String source;

    public LocationSearchResult(String locationId, String locationName, String source) {
        if (locationId == null || locationId.trim().isEmpty()) {
            throw new IllegalArgumentException("Location ID cannot be null or empty");
        }
        if (locationName == null || locationName.trim().isEmpty()) {
            throw new IllegalArgumentException("Location name cannot be null or empty");
        }
        if (source == null || source.trim().isEmpty()) {
            throw new IllegalArgumentException("Source cannot be null or empty");
        }
        this.locationId = locationId.trim();
        this.locationName = locationName.trim();
        this.source = source.trim();
    }

    public String getLocationId() {
        return locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getSource() {
        return source;
    }
} 