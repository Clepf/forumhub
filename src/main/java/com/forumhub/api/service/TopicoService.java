package com.forumhub.api.service;

import com.forumhub.api.dto.request.TopicoRequest;
import com.forumhub.api.dto.response.TopicoResponse;
import com.forumhub.api.entity.Topico;
import com.forumhub.api.entity.Usuario;
import com.forumhub.api.entity.Curso;
import com.forumhub.api.repository.TopicoRepository;
import com.forumhub.api.repository.UsuarioRepository;
import com.forumhub.api.repository.CursoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TopicoService {

    private final TopicoRepository topicoRepo;
    private final UsuarioRepository usuarioRepo;
    private final CursoRepository cursoRepo;

    public TopicoService(TopicoRepository topicoRepo,
                         UsuarioRepository usuarioRepo,
                         CursoRepository cursoRepo) {
        this.topicoRepo = topicoRepo;
        this.usuarioRepo = usuarioRepo;
        this.cursoRepo = cursoRepo;
    }

    @Transactional
    public TopicoResponse criar(TopicoRequest req) {
        validarDados(req);
        Usuario autor = usuarioRepo.findById(req.getAutorId())
                .orElseThrow(() -> new NoSuchElementException("Autor não encontrado"));
        Curso curso = cursoRepo.findById(req.getCursoId())
                .orElseThrow(() -> new NoSuchElementException("Curso não encontrado"));

        Topico topico = Topico.builder()
                .titulo(req.getTitulo())
                .mensagem(req.getMensagem())
                .status(req.getStatus())
                .autor(autor)
                .curso(curso)
                .build();

        Topico salvo = topicoRepo.save(topico);
        return mapToResponse(salvo);
    }

    @Transactional(readOnly = true)
    public Page<TopicoResponse> listar(Pageable pageable) {
        return topicoRepo.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public TopicoResponse buscarPorId(Long id) {
        Topico t = topicoRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tópico não encontrado"));
        return mapToResponse(t);
    }

    @Transactional
    public TopicoResponse atualizar(Long id, TopicoRequest req) {
        Topico existente = topicoRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tópico não encontrado"));
        validarDados(req);
        existente.setTitulo(req.getTitulo());
        existente.setMensagem(req.getMensagem());
        existente.setStatus(req.getStatus());

        Usuario autor = usuarioRepo.findById(req.getAutorId())
                .orElseThrow(() -> new NoSuchElementException("Autor não encontrado"));
        Curso curso = cursoRepo.findById(req.getCursoId())
                .orElseThrow(() -> new NoSuchElementException("Curso não encontrado"));
        existente.setAutor(autor);
        existente.setCurso(curso);

        return mapToResponse(existente);
    }

    @Transactional
    public void deletar(Long id) {
        if (!topicoRepo.existsById(id)) {
            throw new NoSuchElementException("Tópico não encontrado");
        }
        topicoRepo.deleteById(id);
    }

    private void validarDados(TopicoRequest req) {
        // Exemplo de validação de negócio: não aceitar tópicos duplicados
        List<Topico> duplicados = topicoRepo
                .findByTituloContainingIgnoreCase(req.getTitulo(), Pageable.unpaged())
                .stream()
                .filter(t -> t.getMensagem().equals(req.getMensagem()))
                .toList();
        if (!duplicados.isEmpty()) {
            throw new IllegalArgumentException("Já existe um tópico com mesmo título e mensagem");
        }
    }

    private TopicoResponse mapToResponse(Topico t) {
        return TopicoResponse.builder()
                .id(t.getId())
                .titulo(t.getTitulo())
                .mensagem(t.getMensagem())
                .dataCriacao(t.getDataCriacao())
                .status(t.getStatus())
                .autorId(t.getAutor().getId())
                .autorNome(t.getAutor().getNome())
                .cursoId(t.getCurso().getId())
                .cursoNome(t.getCurso().getNome())
                .build();
    }
}
