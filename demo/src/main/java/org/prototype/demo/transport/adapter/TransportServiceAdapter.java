package org.prototype.demo.transport.adapter;

import org.prototype.demo.common.service.IExternalService;
import org.prototype.demo.transport.api.TransportAPI;
import org.prototype.demo.transport.model.TransportRequest;
import org.prototype.demo.transport.model.Transport;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TransportServiceAdapter implements IExternalService<TransportRequest, List<Transport>> {
    private final TransportAPI transportService;

    public TransportServiceAdapter(TransportAPI transportService) {
        this.transportService = transportService;
    }

    @Override
    public CompletableFuture<List<Transport>> executeRequest(TransportRequest request) {
        return transportService.searchAirports(request.getFrom());
    }
}