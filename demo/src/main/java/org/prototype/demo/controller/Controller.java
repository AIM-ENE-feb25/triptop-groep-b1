package org.prototype.demo.controller;

import org.prototype.demo.service.Service;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {
    Service service;
    Controller(Service service){
        this.service = service;
    }
    @GetMapping("getData")
    public ResponseEntity<String> getData(){
        return new ResponseEntity<>(service.getData(), HttpStatusCode.valueOf(200));
    }
}
