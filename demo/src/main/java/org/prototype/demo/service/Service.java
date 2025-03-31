package org.prototype.demo.service;

import org.prototype.demo.externalcontroller.ExternalController;
import org.springframework.http.ResponseEntity;

@org.springframework.stereotype.Service
public class Service {
    ExternalController externalController;
    public Service(ExternalController externalController){
        this.externalController = externalController;
    }


    //regelt logica
    public ResponseEntity getResponse(){
        return null;
    }
}
