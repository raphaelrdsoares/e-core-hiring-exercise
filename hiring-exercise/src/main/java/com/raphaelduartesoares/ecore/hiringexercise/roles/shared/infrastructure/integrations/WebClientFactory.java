package com.raphaelduartesoares.ecore.hiringexercise.roles.shared.infrastructure.integrations;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientFactory {
    public WebClient getClient(final String url) {
        return WebClient.builder().defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl(url).build();
    }
}