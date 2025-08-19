package com.forumhub.api.controller;

import com.forumhub.api.dto.request.TopicoRequest;
import com.forumhub.api.dto.response.TopicoResponse;
import com.forumhub.api.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoService service;

    public TopicoController(TopicoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TopicoResponse> criar(@Valid @RequestBody TopicoRequest req) {
        TopicoResponse resp = service.criar(req);
        return ResponseEntity
                .created(URI.create("/topicos/" + resp.getId()))  // <— URI agora reconhecido
                .body(resp);
    }

    @GetMapping
    public ResponseEntity<Page<TopicoResponse>> listar(Pageable pageable,
                                                       @RequestParam(required = false) String curso,
                                                       @RequestParam(required = false) String status) {
        Page<TopicoResponse> page;
        if (curso != null) {
            page = service.listarPorCurso(curso, pageable);
        } else if (status != null) {
            page = service.listarPorStatus(status, pageable);
        } else {
            page = service.listar(pageable);
        }
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody TopicoRequest req) {
        return ResponseEntity.ok(service.atualizar(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}