package org.prototype.demo.API;

import org.springframework.stereotype.Service;

import javax.naming.ServiceUnavailableException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
@Service
public class APICallStrategyStrategy implements APIServiceActionStrategy {
    @Override
    public String getData() throws ServiceUnavailableException {
            return makeAPICall();
    }

    public String makeAPICall() throws ServiceUnavailableException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://booking-com.p.rapidapi.com/v1/hotels/nearby-cities?longitude=-18.5333&latitude=65.9667&locale=en-gb"))
                    .header("x-rapidapi-key", "c458ddf3a4mshaa3cd5d4a31d629p1ef3c9jsnc435c230a67a")
                    .header("x-rapidapi-host", "booking-com.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        }catch(Exception e){
            throw new ServiceUnavailableException();
        }
    }
}
