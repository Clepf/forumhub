package com.forumhub.api.controller;

import com.forumhub.api.dto.request.CursoRequest;
import com.forumhub.api.dto.response.CursoResponse;
import com.forumhub.api.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService service;

    public CursoController(CursoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CursoResponse> criar(@Valid @RequestBody CursoRequest req) {
        CursoResponse resp = service.criar(req);
        return ResponseEntity
                .created(URI.create("/cursos/" + resp.getId()))
                .body(resp);
    }

    @GetMapping
    public ResponseEntity<List<CursoResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CursoRequest req) {
        return ResponseEntity.ok(service.atualizar(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}