package org.prototype.demo.hotel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TripAdvisorHotelResponse {
    @JsonProperty("data")
    private List<HotelData> data;

    @Data
    public static class HotelData {
        @JsonProperty("hotelId")
        private String hotelId;
        
        @JsonProperty("name")
        private String name;
        
        @JsonProperty("location")
        private Location location;
        
        @JsonProperty("price")
        private Price price;
        
        @JsonProperty("rating")
        private Rating rating;
    }

    @Data
    public static class Location {
        @JsonProperty("address")
        private String address;
        
        @JsonProperty("city")
        private String city;
        
        @JsonProperty("country")
        private String country;
    }

    @Data
    public static class Price {
        @JsonProperty("amount")
        private double amount;
        
        @JsonProperty("currency")
        private String currency;
    }

    @Data
    public static class Rating {
        @JsonProperty("value")
        private double value;
        
        @JsonProperty("count")
        private int count;
    }
} 