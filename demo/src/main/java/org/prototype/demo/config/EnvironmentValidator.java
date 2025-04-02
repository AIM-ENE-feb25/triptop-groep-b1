package org.prototype.demo.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentValidator {

    @Value("${tripadvisor.api.key}")
    private String apiKey;

    @Value("${tripadvisor.api.host}")
    private String apiHost;

    @PostConstruct
    public void validateEnvironment() {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalStateException("TRIPADVISOR_API_KEY environment variable is not set");
        }
        if (apiHost == null || apiHost.trim().isEmpty()) {
            throw new IllegalStateException("TRIPADVISOR_API_HOST environment variable is not set");
        }
    }
} 