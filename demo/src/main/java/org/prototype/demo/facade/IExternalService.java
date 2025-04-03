package org.prototype.demo.facade;

public interface IExternalService {
    Response execute(Request request);
    boolean validate(Response response);
} 