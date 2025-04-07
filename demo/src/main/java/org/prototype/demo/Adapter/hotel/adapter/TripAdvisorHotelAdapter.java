package org.prototype.demo.Adapter.hotel.adapter;

import org.prototype.demo.Adapter.hotel.model.Room;
import org.prototype.demo.Adapter.hotel.model.TripAdvisorHotelResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TripAdvisorHotelAdapter implements HotelAdapter {

    @Override
    public List<Room> adapt(TripAdvisorHotelResponse response) {
        if (!response.isStatus()) {
            throw new RuntimeException(formatErrorMessage(response.getMessage()));
        }

        if (response.getData() == null || response.getData().getData() == null
                || response.getData().getData().isEmpty()) {
            return List.of();
        }

        return response.getData().getData().stream()
                .map(hotelData -> new Room(
                        hotelData.getTitle(),
                        getPriceDisplay(hotelData.getCommerceInfo()),
                        getRating(hotelData.getBubbleRating()),
                        hotelData.getSecondaryInfo()))
                .collect(Collectors.toList());
    }

    private String getPriceDisplay(TripAdvisorHotelResponse.CommerceInfo commerceInfo) {
        if (commerceInfo == null || commerceInfo.getPriceForDisplay() == null) {
            return "Price not available";
        }
        return commerceInfo.getPriceForDisplay().getText();
    }

    private double getRating(TripAdvisorHotelResponse.BubbleRating bubbleRating) {
        if (bubbleRating == null) {
            return 0.0;
        }
        return bubbleRating.getRating();
    }

    private String formatErrorMessage(Object message) {
        if (message instanceof List) {
            List<?> errors = (List<?>) message;
            return errors.stream()
                    .map(error -> error.toString())
                    .collect(Collectors.joining(", "));
        }
        return message != null ? message.toString() : "Unknown error";
    }
}