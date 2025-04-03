package org.prototype.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {
    @Value("${tripadvisor.api.key}")
    private String apiKey;

    @Value("${tripadvisor.api.host}")
    private String apiHost;

    public String getApiKey() {
        return apiKey;
    }

    public String getApiHost() {
        return apiHost;
    }
} 