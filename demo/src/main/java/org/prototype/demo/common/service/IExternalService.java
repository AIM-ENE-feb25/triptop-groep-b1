package org.prototype.demo.common.service;

import java.util.concurrent.CompletableFuture;

public interface IExternalService<RequestType, ResponseType> {
    CompletableFuture<ResponseType> executeRequest(RequestType request);
}