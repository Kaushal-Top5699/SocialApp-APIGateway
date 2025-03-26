package com.kaushal.API_Gateway_Service.service;

import com.kaushal.API_Gateway_Service.dto.LoginRequest;
import com.kaushal.API_Gateway_Service.dto.SignupRequest;
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

    /* AUTH API CALLS START */

    public Mono<String> loginUser(String email, String password) {
        return webClient.post()
                .uri("/user/login")
                .bodyValue(new LoginRequest(email, password))
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> signupUser(String email, String password,
                                   String firstName, String lastName,
                                   String gender, String dob, String phoneNum,
                                   String userImage) {
        return webClient.post()
                .uri("/user/signup")
                .bodyValue(new SignupRequest(email, password,
                        firstName, lastName, gender,
                        dob, phoneNum, userImage))
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

    public Mono<Boolean> checkTokenValidity(String token, String email) {
        System.out.println("Token Validity Check Begin...");
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/user/check-token-validity")
                        .queryParam("email", email)
                        .build())
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Boolean>() {
                });
    }

    /* AUTH API CALLS END */
}
