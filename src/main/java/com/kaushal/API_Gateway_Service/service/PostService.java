package com.kaushal.API_Gateway_Service.service;

import com.kaushal.API_Gateway_Service.dto.Post;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;

@Service
public class PostService {
    private final WebClient webClient;

    public PostService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8082/").build();
    }

    /* POST API CALLS START */

    public Mono<List<Post>> getAllPosts(String token, String email) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/post/get-all-posts")
                        .queryParam("email", email)
                        .build())
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Post>>() {});
    }

    public Mono<String> createNewPost(String imageURL, String userImageURL,
                                      String userEmail, String username, String totalLikes,
                                      String totalComments, String caption, String token, String email) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/post/create-post")
                        .queryParam("email", email)
                        .build())
                .header("Authorization", "Bearer " + token)
                .bodyValue(new Post(imageURL, userImageURL, userEmail,
                        username, totalLikes, totalComments, caption))
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> updatePost(String token, String email, String postID, Post updatedPost) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/post/update-post")
                        .queryParam("email", email)
                        .build())
                .header("Authorization", "Bearer " + token)
                .header("postID", postID)
                .bodyValue(new Post(updatedPost.getCaption()))
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<HashMap<String, String>> findPostById(String token, String email, String postID) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/post/find-post-by-id")
                        .queryParam("email", email)
                        .build())
                .header("Authorization", "Bearer " + token)
                .header("postID", postID)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<HashMap<String, String>>() {});
    }

    public Mono<String> deletePost(String token, String email, String postID) {
        return webClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/post/delete-post")
                        .queryParam("email", email)
                        .build())
                .header("Authorization", "Bearer " + token)
                .header("postID", postID)
                .retrieve()
                .bodyToMono(String.class);
    }

    /* POST API CALLS END */
}
