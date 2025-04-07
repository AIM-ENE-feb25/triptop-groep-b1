package org.prototype.demo.Facade;

import org.prototype.demo.Facade.Interfaces.IExternalService;
import org.prototype.demo.Facade.Interfaces.IExternalServiceFacade;
import org.prototype.demo.Facade.Models.LocationSearchCriteria;
import org.prototype.demo.Facade.Models.LocationSearchResult;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ExternalServiceFacade implements IExternalServiceFacade {
    private final CopyOnWriteArrayList<IExternalService> externalServices;

    public ExternalServiceFacade(List<IExternalService> externalServices) {
        if (externalServices == null || externalServices.isEmpty()) {
            throw new IllegalArgumentException("External services list cannot be null or empty");
        }
        this.externalServices = new CopyOnWriteArrayList<>(externalServices);
    }

    @Override
    public List<LocationSearchResult> searchLocations(LocationSearchCriteria criteria) {
        if (criteria == null) {
            throw new IllegalArgumentException("Search criteria cannot be null");
        }

        return externalServices.parallelStream()
                .flatMap(service -> {
                    try {
                        return service.searchLocations(criteria).stream();
                    } catch (Exception e) {
                        System.err.println("Error searching locations with service: " + e.getMessage());
                        return java.util.stream.Stream.empty();
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public void addExternalService(IExternalService service) {
        if (service == null) {
            throw new IllegalArgumentException("Service cannot be null");
        }
        externalServices.add(service);
    }

    @Override
    public void removeExternalService(IExternalService service) {
        if (service == null) {
            throw new IllegalArgumentException("Service cannot be null");
        }
        externalServices.remove(service);
    }
}