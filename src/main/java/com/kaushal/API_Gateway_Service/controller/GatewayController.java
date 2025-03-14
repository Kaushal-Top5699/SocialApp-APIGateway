package com.kaushal.API_Gateway_Service.controller;

import com.kaushal.API_Gateway_Service.dto.LoginRequest;
import com.kaushal.API_Gateway_Service.dto.SignupRequest;
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

    /* USER AUTH ROUTES START */
    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody LoginRequest request) {
        return authService.loginUser(request.getEmail(), request.getPassword())
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(401).body("Unauthorized"));
    }

    @PostMapping("/signup")
    public Mono<ResponseEntity<String>> signup(@RequestBody SignupRequest request) {
        return authService.signupUser(request.getEmail(), request.getPassword(),
                request.getFirstName(), request.getLastName(), request.getGender(),
                request.getDob(), request.getPhoneNum(), request.getUserImage())
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(400).body("Bad Request"));
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

    @GetMapping("/check-token-validity")
    public Mono<ResponseEntity<Boolean>> checkTokenValidity(@RequestHeader("Authorization") String authHeader,
                                                           @RequestParam String email) {
        System.out.println("Token validity check entered...");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
        String token = authHeader.replace("Bearer ", "").trim();
        return authService.checkTokenValidity(token, email)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(401).body(false));
    }

    /* USER AUTH ROUTES END */
}
