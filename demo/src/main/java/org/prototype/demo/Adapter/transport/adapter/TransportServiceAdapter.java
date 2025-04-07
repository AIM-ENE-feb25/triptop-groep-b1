package org.prototype.demo.Adapter.transport.adapter;

import org.prototype.demo.Adapter.common.service.IExternalService;
import org.prototype.demo.Adapter.transport.api.TransportAPI;
import org.prototype.demo.Adapter.transport.model.TransportRequest;
import org.prototype.demo.Adapter.transport.model.Transport;
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