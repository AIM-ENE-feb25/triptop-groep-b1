package org.prototype.demo.Facade.Services;

import org.prototype.demo.Facade.Interfaces.IExternalService;

public class TripAdvisorService implements IExternalService {
    private HttpClient apiClient;
    private Credentials credentials;

    @Override
    public Response execute(Request request) {
        
        return null;
    }

    @Override
    public boolean validate(Response response) {
        
        return false;
    }

    private Response mapResponse(RawResponse rawResponse) {
      
        return null;
    }
}