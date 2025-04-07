package org.prototype.demo.Facade.Services;

import org.prototype.demo.Facade.Interfaces.IExternalService;

import java.net.http.HttpClient;

public class BookingService implements IExternalService {
    private HttpClient apiClient;
    private Credentials credentials;

    @Override
    public Response execute(Request request) {
        // Implementation here
        return null;
    }

    @Override
    public boolean validate(Response response) {
        // Implementation here
        return false;
    }

    private Response mapResponse(RawResponse rawResponse) {
        // Implementation here
        return null;
    }
}