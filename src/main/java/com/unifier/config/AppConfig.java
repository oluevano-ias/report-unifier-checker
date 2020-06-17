package com.unifier.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Value("${endpoint.readTimeoutMilliseconds}")
    private Integer readTimeout;

    @Value("${endpoint.connectTimeoutMilliseconds}")
    private Integer connectTimeout;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(requestFactory());
    }

    private HttpComponentsClientHttpRequestFactory requestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(readTimeout);
        factory.setConnectTimeout(connectTimeout);
        return factory;
    }

}
