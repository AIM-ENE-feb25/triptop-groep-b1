package org.prototype.demo.common.service;

import lombok.extern.slf4j.Slf4j;
import org.prototype.demo.common.model.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExternalServiceFacade {
    private final List<IExternalService> services;

    public ExternalServiceFacade(List<IExternalService> services) {
        this.services = services;
    }

    public List<CompletableFuture<ApiResponse>> executeRequests(Object request) {
        return services.stream()
            .map(service -> service.executeRequest(request))
            .collect(Collectors.toList());
    }

    public CompletableFuture<List<ApiResponse>> executeAllRequests(Object request) {
        List<CompletableFuture<ApiResponse>> futures = executeRequests(request);
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
            .thenApply(v -> futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList()));
    }
} 