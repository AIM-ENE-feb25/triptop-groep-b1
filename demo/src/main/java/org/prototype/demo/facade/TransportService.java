package org.prototype.demo.facade;

import org.prototype.demo.transport.api.TransportAPI;
import org.prototype.demo.transport.model.TransportRequest;
import org.prototype.demo.transport.model.Transport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TransportService implements IExternalService {
    private final TransportAPI transportAPI;

    @Autowired
    public TransportService(TransportAPI transportAPI) {
        this.transportAPI = transportAPI;
    }

    @Override
    public Response execute(Request request) {
        if (request instanceof TransportRequest transportRequest) {
            CompletableFuture<List<Transport>> transportFuture = transportAPI.searchAirports(
                transportRequest.getLocation()
            );
            
            // Convert the CompletableFuture to a Response
            return new TransportResponse(transportFuture.join());
        }
        throw new IllegalArgumentException("Invalid request type for TransportService");
    }

    @Override
    public boolean validate(Response response) {
        return response instanceof TransportResponse;
    }
} 