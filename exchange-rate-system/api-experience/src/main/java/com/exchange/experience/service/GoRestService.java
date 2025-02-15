package com.exchange.experience.service;

import com.exchange.experience.model.dto.GoRestUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GoRestService {
    private final WebClient webClient;

    public GoRestService(@Value("${gorest.api.url}") String gorestUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(gorestUrl)
                .build();
    }

    public Mono<GoRestUser> getUser(Long userId) {
        return webClient.get()
                .uri("/users/{id}", userId)
                .retrieve()
                .bodyToMono(GoRestUser.class);
    }
}
