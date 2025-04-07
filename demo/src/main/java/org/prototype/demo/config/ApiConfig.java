package org.prototype.demo.config;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;

@Configuration
@Getter
public class ApiConfig {
    
    @Value("${tripadvisor.api.key}")
    private String apiKey;
    
    @Value("${tripadvisor.api.host}")
    private String apiHost;

    @Bean
    public AsyncHttpClient asyncHttpClient() {
        return new DefaultAsyncHttpClient();
    }
} 