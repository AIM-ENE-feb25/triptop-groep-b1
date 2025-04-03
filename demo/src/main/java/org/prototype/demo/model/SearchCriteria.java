package org.prototype.demo.model;

import org.prototype.demo.facade.Request;
import org.prototype.demo.hotel.model.DateRange;

public class SearchCriteria extends Request {
    private final String location;
    private final DateRange dateRange;

    public SearchCriteria(String location, DateRange dateRange) {
        this.location = location;
        this.dateRange = dateRange;
    }

    public String getLocation() {
        return location;
    }

    public DateRange getDateRange() {
        return dateRange;
    }
} 