package com.kaushal.API_Gateway_Service.controller;

import com.kaushal.API_Gateway_Service.dto.LoginRequest;
import com.kaushal.API_Gateway_Service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@RestController
@RequestMapping("/gateway")
public class GatewayController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody LoginRequest request) {
        return authService.loginUser(request.getEmail(), request.getPassword())
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(401).body("Unauthorized"));
    }

    @GetMapping("/current-user-info")
    public Mono<ResponseEntity<HashMap<String, String>>> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
        String token = authHeader.replace("Bearer ", "").trim();
        return authService.getCurrentUser(token)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/given-user")
    public Mono<ResponseEntity<HashMap<String, String>>> getGivenUser
            (@RequestHeader("Authorization") String authHeader,
             @RequestHeader String userID,
             @RequestParam String email) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
        String token = authHeader.replace("Bearer ", "").trim();
        return authService.getGivenUser(token, email, userID)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
