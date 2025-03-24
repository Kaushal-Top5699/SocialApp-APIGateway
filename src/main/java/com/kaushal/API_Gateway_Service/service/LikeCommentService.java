package com.kaushal.API_Gateway_Service.service;

import com.kaushal.API_Gateway_Service.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class LikeCommentService {

    private final WebClient webClient;

    public LikeCommentService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8083/").build();
    }

    /* LIKE API CALLS START */

    public Mono<String> createNewLike(String token, String email, String postID, String userID) {
        return webClient.post()
                .uri("/post-like")
                .bodyValue(new Like(token, email, postID, userID))
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> deleteLike(String token, String email, String likeID) {
        return webClient.post()
                .uri("/delete-like")
                .header("Content-Type", "application/json")
                .bodyValue(new DeleteLike(token, email, likeID))
                .retrieve()
                .bodyToMono(String.class);

    }

    public Mono<LikeResponse> countLikes(String postID) {
        return webClient.get()
                .uri("/likes-count")
                .header("postid", postID)
                .retrieve()
                .bodyToMono(LikeResponse.class);
    }

    /* LIKE API CALLS END */

    /* COMMENT API CALLS START */

    public Mono<String> postComment(String token, String email,
                                    String commentTxt, String postID,
                                    String userID) {
        return webClient.post()
                .uri("/post-comment")
                .bodyValue(new Comment(token, email, commentTxt, postID, userID))
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> editComment(String token, String email, String postID, String userID, String commentID,
                                    String updatedComment) {
        return webClient.post()
                .uri("/edit-comment")
                .bodyValue(new CommentEdit(token, email, postID, userID, commentID, updatedComment))
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> deleteComment(String token, String email, String commentID) {
        return webClient.post()
                .uri("/delete-comment")
                .bodyValue(new CommentDelete(token, email, commentID))
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<CommentResponse> countComments(String postID) {
        return webClient.get()
                .uri("/count-comments")
                .header("postid", postID)
                .retrieve()
                .bodyToMono(CommentResponse.class);
    }

    /* LIKE API CALLS END */

}
