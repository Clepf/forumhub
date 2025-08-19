package com.forumhub.api.service;

import com.forumhub.api.dto.request.CursoRequest;
import com.forumhub.api.dto.response.CursoResponse;
import com.forumhub.api.entity.Curso;
import com.forumhub.api.repository.CursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CursoService {

    private final CursoRepository cursoRepo;

    public CursoService(CursoRepository cursoRepo) {
        this.cursoRepo = cursoRepo;
    }

    @Transactional
    public CursoResponse criar(CursoRequest req) {
        if (cursoRepo.findByNome(req.getNome()).isPresent()) {
            throw new IllegalArgumentException("Curso já existe");
        }
        Curso curso = Curso.builder()
                .nome(req.getNome())
                .categoria(req.getCategoria())
                .build();
        Curso salvo = cursoRepo.save(curso);
        return new CursoResponse(salvo.getId(), salvo.getNome(), salvo.getCategoria());
    }

    @Transactional(readOnly = true)
    public List<CursoResponse> listar() {
        return cursoRepo.findAll().stream()
                .map(c -> new CursoResponse(c.getId(), c.getNome(), c.getCategoria()))
                .toList();
    }

    @Transactional(readOnly = true)
    public CursoResponse buscarPorId(Long id) {
        Curso c = cursoRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Curso não encontrado"));
        return new CursoResponse(c.getId(), c.getNome(), c.getCategoria());
    }

    @Transactional
    public CursoResponse atualizar(Long id, CursoRequest req) {
        Curso existente = cursoRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Curso não encontrado"));

        // Verificar se já existe outro curso com o mesmo nome
        cursoRepo.findByNome(req.getNome())
                .filter(c -> !c.getId().equals(id))
                .ifPresent(c -> {
                    throw new IllegalArgumentException("Já existe outro curso com esse nome");
                });

        existente.setNome(req.getNome());
        existente.setCategoria(req.getCategoria());

        Curso atualizado = cursoRepo.save(existente);
        return new CursoResponse(atualizado.getId(), atualizado.getNome(), atualizado.getCategoria());
    }

    @Transactional
    public void deletar(Long id) {
        if (!cursoRepo.existsById(id)) {
            throw new NoSuchElementException("Curso não encontrado");
        }
        cursoRepo.deleteById(id);
    }
}
