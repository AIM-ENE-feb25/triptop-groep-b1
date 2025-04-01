package org.prototype.demo.API;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.naming.ServiceUnavailableException;
import java.io.IOException;

@Component
public class ServiceContext {
    APIServiceActionStrategy apiServiceActionStrategy;

    ServiceContext(@Qualifier("APICallStrategyStrategy") APIServiceActionStrategy apiServiceActionStrategy) {
        this.apiServiceActionStrategy = apiServiceActionStrategy;
    }
    public void setApiServiceActionStrategy(APIServiceActionStrategy apiServiceActionStrategy) {
        this.apiServiceActionStrategy = apiServiceActionStrategy;
    }
    public String getData() throws ServiceUnavailableException {
        return apiServiceActionStrategy.getData();
    }
}
