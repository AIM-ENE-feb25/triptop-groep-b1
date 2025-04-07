package org.prototype.demo.Facade.Interfaces;

import org.prototype.demo.Facade.Models.LocationSearchCriteria;
import org.prototype.demo.Facade.Models.LocationSearchResult;

import java.util.List;

public interface IExternalService {
    List<LocationSearchResult> searchLocations(LocationSearchCriteria criteria);
    void setApiKey(String apiKey);
}