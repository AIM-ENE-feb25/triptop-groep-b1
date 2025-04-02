package org.prototype.demo.service;

import org.prototype.demo.API.FallbackStrategy;
import org.prototype.demo.API.ServiceContext;
import org.springframework.http.ResponseEntity;

import javax.naming.ServiceUnavailableException;
import java.io.IOException;

@org.springframework.stereotype.Service
public class Service {

    ServiceContext serviceContext;
    Service(ServiceContext serviceContext){
        this.serviceContext = serviceContext;
    }
    public String getData()  {
        try {
            try {
                return serviceContext.getData();
            } catch (ServiceUnavailableException e) {
                serviceContext.setApiServiceActionStrategy(new FallbackStrategy());
                return serviceContext.getData();
            }
        }catch(Exception e){
            return "unexpected error has occurred";
        }
    }
}
