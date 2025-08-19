package com.forumhub.api.controller;

import com.forumhub.api.dto.request.LoginRequest;
import com.forumhub.api.dto.response.LoginResponse;
import com.forumhub.api.security.JwtService;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authManager,
                          JwtService jwtService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResponse login(@Validated @RequestBody LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getSenha()));
        String token = jwtService.gerarToken(auth.getName());
        return new LoginResponse(token);
    }
}
