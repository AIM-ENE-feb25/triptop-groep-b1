package org.prototype.demo.Adapter.common.service;

import java.util.concurrent.CompletableFuture;

public interface IExternalService<RequestType, ResponseType> {
    CompletableFuture<ResponseType> executeRequest(RequestType request);
}