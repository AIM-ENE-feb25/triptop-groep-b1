package org.prototype.demo.controller;

import org.prototype.demo.service.Service;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@RestController
public class Controller {
    Service service;
    public Controller(Service service){
        this.service = service;
    }
    @GetMapping("getResponse")
    public String getResposne() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://sky-scanner3.p.rapidapi.com/flights/search-one-way?fromEntityId=PARI&cabinClass=economy"))
                .header("x-rapidapi-key", "c458ddf3a4mshaa3cd5d4a31d629p1ef3c9jsnc435c230a67a")
                .header("x-rapidapi-host", "sky-scanner3.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());


        return "a";
    }


}
