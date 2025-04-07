package org.prototype.demo.Facade;

public interface IExternalService {
    Response execute(Request request);
    boolean validate(Response response);
}