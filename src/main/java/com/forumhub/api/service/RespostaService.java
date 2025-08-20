package com.forumhub.api.service;

import com.forumhub.api.dto.request.RespostaRequest;
import com.forumhub.api.dto.response.RespostaResponse;
import com.forumhub.api.entity.Resposta;
import com.forumhub.api.entity.Topico;
import com.forumhub.api.entity.Usuario;
import com.forumhub.api.repository.RespostaRepository;
import com.forumhub.api.repository.TopicoRepository;
import com.forumhub.api.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RespostaService {

    private final RespostaRepository respostaRepo;
    private final UsuarioRepository usuarioRepo;
    private final TopicoRepository topicoRepo;

    public RespostaService(RespostaRepository respostaRepo,
                           UsuarioRepository usuarioRepo,
                           TopicoRepository topicoRepo) {
        this.respostaRepo = respostaRepo;
        this.usuarioRepo = usuarioRepo;
        this.topicoRepo = topicoRepo;
    }

    @Transactional
    public RespostaResponse criar(RespostaRequest req) {
        Usuario autor = usuarioRepo.findById(req.getAutorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor não encontrado"));
        Topico topico = topicoRepo.findById(req.getTopicoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico não encontrado"));

        Resposta resposta = Resposta.builder()
                .mensagem(req.getMensagem())
                .solucao(req.getSolucao())
                .autor(autor)
                .topico(topico)
                .build();

        Resposta salvo = respostaRepo.save(resposta);
        return mapToResponse(salvo);
    }

    @Transactional(readOnly = true)
    public List<RespostaResponse> listarPorTopico(Long topicoId) {
        if (!topicoRepo.existsById(topicoId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico não encontrado");
        }
        return respostaRepo.findAllByTopicoId(topicoId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RespostaResponse buscarPorId(Long id) {
        Resposta r = respostaRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resposta não encontrada"));
        return mapToResponse(r);
    }

    @Transactional
    public RespostaResponse atualizar(Long id, RespostaRequest req) {
        Resposta existente = respostaRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resposta não encontrada"));
        Usuario autor = usuarioRepo.findById(req.getAutorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor não encontrado"));
        Topico topico = topicoRepo.findById(req.getTopicoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico não encontrado"));

        existente.setMensagem(req.getMensagem());
        existente.setSolucao(req.getSolucao());
        existente.setAutor(autor);
        existente.setTopico(topico);

        return mapToResponse(existente);
    }

    @Transactional
    public void deletar(Long id) {
        if (!respostaRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resposta não encontrada");
        }
        respostaRepo.deleteById(id);
    }

    private RespostaResponse mapToResponse(Resposta r) {
        return RespostaResponse.builder()
                .id(r.getId())
                .mensagem(r.getMensagem())
                .dataCriacao(r.getDataCriacao())
                .solucao(r.getSolucao())
                .autorId(r.getAutor().getId())
                .autorNome(r.getAutor().getNome())
                .topicoId(r.getTopico().getId())
                .build();
    }
}
