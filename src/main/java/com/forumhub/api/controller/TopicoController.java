package com.forumhub.api.controller;

import com.forumhub.api.dto.request.TopicoRequest;
import com.forumhub.api.dto.response.TopicoResponse;
import com.forumhub.api.service.TopicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
@Tag(name = "Tópicos", description = "Operações relacionadas aos tópicos do fórum")
@SecurityRequirement(name = "Bearer Authentication")
public class TopicoController {

    private final TopicoService service;

    public TopicoController(TopicoService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Criar novo tópico", description = "Cria um novo tópico no fórum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tópico criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Token JWT inválido ou ausente"),
            @ApiResponse(responseCode = "409", description = "Tópico já existe com mesmo título e mensagem")
    })
    public ResponseEntity<TopicoResponse> criar(@Valid @RequestBody TopicoRequest req) {
        if (service.existsByTituloAndMensagem(req.getTitulo(), req.getMensagem())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(null);
        }
        TopicoResponse resp = service.criar(req);
        URI uri = URI.create("/topicos/" + resp.getId());
        return ResponseEntity.created(uri).body(resp);
    }

    @GetMapping
    @Operation(summary = "Listar tópicos", description = "Lista todos os tópicos com paginação e filtros opcionais")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tópicos retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token JWT inválido ou ausente")
    })
    public ResponseEntity<Page<TopicoResponse>> listar(
            Pageable pageable,
            @Parameter(description = "Filtrar por nome do curso")
            @RequestParam(required = false) String curso,
            @Parameter(description = "Filtrar por status do tópico")
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
    @Operation(summary = "Buscar tópico por ID", description = "Retorna os detalhes de um tópico específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tópico encontrado"),
            @ApiResponse(responseCode = "401", description = "Token JWT inválido ou ausente"),
            @ApiResponse(responseCode = "404", description = "Tópico não encontrado")
    })
    public ResponseEntity<TopicoResponse> buscarPorId(
            @Parameter(description = "ID do tópico", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tópico", description = "Atualiza os dados de um tópico existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tópico atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Token JWT inválido ou ausente"),
            @ApiResponse(responseCode = "404", description = "Tópico não encontrado"),
            @ApiResponse(responseCode = "409", description = "Já existe outro tópico com mesmo título e mensagem")
    })
    public ResponseEntity<TopicoResponse> atualizar(
            @Parameter(description = "ID do tópico", required = true)
            @PathVariable Long id,
            @Valid @RequestBody TopicoRequest req) {
        return ResponseEntity.ok(service.atualizar(id, req));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar tópico", description = "Remove um tópico do fórum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tópico deletado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token JWT inválido ou ausente"),
            @ApiResponse(responseCode = "404", description = "Tópico não encontrado")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do tópico", required = true)
            @PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}