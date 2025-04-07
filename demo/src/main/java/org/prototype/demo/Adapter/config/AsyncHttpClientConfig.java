package org.prototype.demo.Adapter.config;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AsyncHttpClientConfig {

    @Bean
    public AsyncHttpClient asyncHttpClient() {
        return Dsl.asyncHttpClient();
    }
}