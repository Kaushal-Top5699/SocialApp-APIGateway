package com.kaushal.API_Gateway_Service.service;

import com.kaushal.API_Gateway_Service.dto.LoginRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Service
public class AuthService {

    private final WebClient webClient;

    public AuthService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/").build();
    }

    public Mono<String> loginUser(String email, String password) {
        return webClient.post()
                .uri("/user/login")
                .bodyValue(new LoginRequest(email, password))
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<HashMap<String, String>> getCurrentUser(String token) {
        return webClient.get()
                .uri("/user/user-info")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<HashMap<String, String>>() {});
    }

    public Mono<HashMap<String, String>> getGivenUser(String token, String email, String userID) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/user/other-user-info")
                        .queryParam("email", email)  // ✅ Pass email as a query parameter
                        .build())
                .header("Authorization", "Bearer " + token)  // ✅ Pass token in Authorization header
                .header("userID", userID)  // ✅ Pass userID in a header
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }
}
