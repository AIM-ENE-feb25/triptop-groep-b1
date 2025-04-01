package org.prototype.demo.API;

import javax.naming.ServiceUnavailableException;
import java.io.IOException;

public interface APIServiceActionStrategy {
    public String getData() throws ServiceUnavailableException;
}
