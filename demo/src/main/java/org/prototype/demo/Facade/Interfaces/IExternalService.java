package org.prototype.demo.Facade.Interfaces;

public interface IExternalService {
    Response execute(Request request);
    boolean validate(Response response);
}