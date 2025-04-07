package org.prototype.demo.Adapter.hotel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    private String title;
    private String price;
    private double rating;
    private String location;
}