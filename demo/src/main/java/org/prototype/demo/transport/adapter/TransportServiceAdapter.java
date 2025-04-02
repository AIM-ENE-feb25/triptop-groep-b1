package org.prototype.demo.transport.adapter;

import org.prototype.demo.common.service.IExternalService;
import org.prototype.demo.transport.api.TransportAPI;
import org.prototype.demo.transport.model.TransportRequest;
import java.util.List;

public class TransportServiceAdapter implements IExternalService {
    private final TransportAPI transportService;

    public TransportServiceAdapter(TransportAPI transportService) {
        this.transportService = transportService;
    }

    @Override
    public Object executeRequest(Object request) {
        if (request instanceof TransportRequest) {
            TransportRequest transportRequest = (TransportRequest) request;
            return transportService.searchRoutes(
                transportRequest.getFrom(),
                transportRequest.getTo()
            );
        }
        throw new IllegalArgumentException("Invalid request type for TransportServiceAdapter");
    }
} 