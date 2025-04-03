package org.prototype.demo.common.service;

import org.prototype.demo.common.model.ApiResponse;
import java.util.concurrent.CompletableFuture;

public interface IExternalService {
    CompletableFuture<ApiResponse> executeRequest(Object request);
    boolean validate(ApiResponse response);
} 