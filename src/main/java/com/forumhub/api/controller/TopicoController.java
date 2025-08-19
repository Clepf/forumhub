package com.forumhub.api.controller;

import com.forumhub.api.dto.request.TopicoRequest;
import com.forumhub.api.dto.response.TopicoResponse;
import com.forumhub.api.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoService service;

    public TopicoController(TopicoService service) {
        this.service = service;
    }

    // Criar um novo tópico
    @PostMapping
    public ResponseEntity<TopicoResponse> criar(@Valid @RequestBody TopicoRequest req) {
        TopicoResponse resp = service.criar(req);
        return ResponseEntity
                .created(URI.create("/topicos/" + resp.getId()))
                .body(resp);
    }

    // Listar tópicos com paginação e filtros opcionais
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

    // Detalhar um tópico por ID
    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponse> buscarPorId(@PathVariable Long id) {
        TopicoResponse resp = service.buscarPorId(id);
        return ResponseEntity.ok(resp);
    }

    // Atualizar um tópico existente
    @PutMapping("/{id}")
    public ResponseEntity<TopicoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody TopicoRequest req) {
        TopicoResponse resp = service.atualizar(id, req);
        return ResponseEntity.ok(resp);
    }

    // Excluir um tópico
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
