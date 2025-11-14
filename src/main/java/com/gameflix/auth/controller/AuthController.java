// src/main/java/com/gameflix/auth/controller/AuthController.java
package com.gameflix.auth.controller;

import com.gameflix.auth.dto.AuthRequest;
import com.gameflix.auth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    public AuthController(UserService userService) { this.userService = userService; }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@Valid @RequestBody AuthRequest req) {
        userService.register(req.getUsername(), req.getPassword());
        return ResponseEntity.status(201).body(Map.of("message","User registered successfully"));
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest req) {
        boolean ok = userService.login(req.getUsername(), req.getPassword());
        if (ok) return ResponseEntity.ok(Map.of("message","Login successful"));
        return ResponseEntity.status(401).body(Map.of("message","Invalid username or password"));
    }

    @GetMapping("/health")
    public Map<String,String> health() { return Map.of("status","ok"); }
}
