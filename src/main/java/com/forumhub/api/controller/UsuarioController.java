package com.forumhub.api.controller;

import com.forumhub.api.dto.request.UsuarioRequest;
import com.forumhub.api.dto.response.UsuarioResponse;
import com.forumhub.api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> criar(@Valid @RequestBody UsuarioRequest req) {
        UsuarioResponse resp = service.criar(req);
        return ResponseEntity
                .created(URI.create("/usuarios/" + resp.getId()))
                .body(resp);
    }
}
