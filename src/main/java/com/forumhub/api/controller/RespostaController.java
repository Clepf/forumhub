package com.forumhub.api.controller;

import com.forumhub.api.dto.request.RespostaRequest;
import com.forumhub.api.dto.response.RespostaResponse;
import com.forumhub.api.service.RespostaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/respostas")
@Tag(name = "Respostas", description = "Operações relacionadas às respostas de um tópico")
@SecurityRequirement(name = "Bearer Authentication")
public class RespostaController {

    private final RespostaService service;

    public RespostaController(RespostaService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Criar nova resposta", description = "Adiciona uma resposta a um tópico existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Resposta criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Token JWT inválido ou ausente"),
            @ApiResponse(responseCode = "404", description = "Autor ou Tópico não encontrados")
    })
    public ResponseEntity<RespostaResponse> criar(@Valid @RequestBody RespostaRequest req) {
        RespostaResponse resp = service.criar(req);
        URI uri = URI.create("/respostas/" + resp.getId());
        return ResponseEntity.created(uri).body(resp);
    }

    @GetMapping("/topico/{topicoId}")
    @Operation(summary = "Listar respostas por tópico", description = "Retorna todas as respostas de um tópico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Respostas retornadas"),
            @ApiResponse(responseCode = "401", description = "Token JWT inválido ou ausente"),
            @ApiResponse(responseCode = "404", description = "Tópico não encontrado")
    })
    public ResponseEntity<List<RespostaResponse>> listarPorTopico(
            @PathVariable Long topicoId) {
        List<RespostaResponse> lista = service.listarPorTopico(topicoId);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar resposta por ID", description = "Busca uma resposta específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resposta encontrada"),
            @ApiResponse(responseCode = "401", description = "Token JWT inválido ou ausente"),
            @ApiResponse(responseCode = "404", description = "Resposta não encontrada")
    })
    public ResponseEntity<RespostaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar resposta", description = "Atualiza o conteúdo de uma resposta existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resposta atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Token JWT inválido ou ausente"),
            @ApiResponse(responseCode = "404", description = "Resposta, Autor ou Tópico não encontrados")
    })
    public ResponseEntity<RespostaResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody RespostaRequest req) {
        return ResponseEntity.ok(service.atualizar(id, req));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar resposta", description = "Remove uma resposta pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Resposta deletada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token JWT inválido ou ausente"),
            @ApiResponse(responseCode = "404", description = "Resposta não encontrada")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
