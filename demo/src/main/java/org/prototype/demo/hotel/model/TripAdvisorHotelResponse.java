package org.prototype.demo.hotel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TripAdvisorHotelResponse {
    private boolean status;
    @JsonProperty("message")
    private Object message; // Can be String or List<ValidationError>
    private long timestamp;
    private DataWrapper data;

    @Data
    public static class DataWrapper {
        private List<HotelData> data;
    }

    @Data
    public static class HotelData {
        private String id;
        private String title;
        private String primaryInfo;
        private String secondaryInfo;
        private BubbleRating bubbleRating;
        private CommerceInfo commerceInfo;
    }

    @Data
    public static class BubbleRating {
        private String count;
        private double rating;
    }

    @Data
    public static class CommerceInfo {
        private PriceDisplay priceForDisplay;
        private String provider;
    }

    @Data
    public static class PriceDisplay {
        private String text;
    }

    @Data
    public static class ValidationError {
        private String checkIn;
        private String checkOut;
    }
}