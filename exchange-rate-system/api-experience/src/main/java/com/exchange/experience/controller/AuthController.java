package com.exchange.experience.controller;

import com.exchange.experience.config.JwtTokenUtil;
import com.exchange.experience.model.dto.AuthRequest;
import com.exchange.experience.model.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/get-token")
    public Mono<AuthResponse> getToken(@RequestBody AuthRequest request) {
        if ("admin".equals(request.getUsername()) && "12345".equals(request.getPassword())) {
            AuthResponse response = new AuthResponse();
            response.setToken(jwtTokenUtil.generateToken(request.getUsername()));
            return Mono.just(response);
        }
        return Mono.error(new RuntimeException("Credenciales inválidas"));
    }
}