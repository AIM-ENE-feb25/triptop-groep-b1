package org.prototype.demo.common.client;

import org.prototype.demo.common.service.IExternalService;
import java.util.HashMap;
import java.util.Map;

public class ServiceClient {
    private final Map<String, IExternalService> services;

    public ServiceClient() {
        this.services = new HashMap<>();
    }

    public Object executeServiceRequest(String serviceId, Object request) {
        IExternalService service = services.get(serviceId);
        if (service == null) {
            throw new IllegalArgumentException("Service not found: " + serviceId);
        }
        return service.executeRequest(request);
    }

    public void addService(String serviceId, IExternalService service) {
        services.put(serviceId, service);
    }
} 