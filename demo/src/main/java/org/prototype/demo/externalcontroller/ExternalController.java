package org.prototype.demo.externalcontroller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Controller
public class ExternalController {


    public String getResponse() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://sky-scanner3.p.rapidapi.com/flights/airports"))
                .header("x-rapidapi-key", "c458ddf3a4mshaa3cd5d4a31d629p1ef3c9jsnc435c230a67a")
                .header("x-rapidapi-host", "sky-scanner3.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        return "Success";

    }
}
