package com.kaushal.API_Gateway_Service.service;

import com.kaushal.API_Gateway_Service.dto.Like;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class LikeCommentService {

    private final WebClient webClient;

    public LikeCommentService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8083/").build();
    }

    public Mono<String> createNewLike(String token, String email, String postID, String userID) {
        return webClient.post()
                .uri("/post-like")
                .bodyValue(new Like(token, email, postID, userID))
                .retrieve()
                .bodyToMono(String.class);
    }
}
