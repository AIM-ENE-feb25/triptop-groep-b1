package org.prototype.demo.Adapter.transport.adapter;

import org.prototype.demo.Adapter.transport.model.Transport;
import org.prototype.demo.Adapter.transport.model.TripAdvisorResponse;
import java.util.List;

public interface TransportAdapter {
    /**
     * Adapts external transport data into our domain model
     * 
     * @param externalData The raw data from the external API
     * @return List of Transport objects in our domain model
     */
    List<Transport> adapt(TripAdvisorResponse externalData);
}