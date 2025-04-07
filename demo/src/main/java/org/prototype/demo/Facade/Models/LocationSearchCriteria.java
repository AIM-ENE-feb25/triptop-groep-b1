package org.prototype.demo.Facade.Models;

public class LocationSearchCriteria {
    private final String location;
    private final String languageCode;

    public LocationSearchCriteria(String location, String languageCode) {
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be null or empty");
        }
        if (languageCode == null || languageCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Language code cannot be null or empty");
        }
        this.location = location.trim();
        this.languageCode = languageCode.trim();
    }

    public String getLocation() {
        return location;
    }

    public String getLanguageCode() {
        return languageCode;
    }
} 