package demo.tt.lucasstrategy.API;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BookingAPICall implements APIStrategy{
    @Override
    public String getData() {
    try{
        return makeAPICall();
    }catch (Exception e){
        return "oh no";
    }
    }

    public String makeAPICall() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://booking-com.p.rapidapi.com/v1/metadata/exchange-rates?locale=en-gb&currency=AED"))
                .header("x-rapidapi-key", "c458ddf3a4mshaa3cd5d4a31d629p1ef3c9jsnc435c230a67a")
                .header("x-rapidapi-host", "booking-com.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
