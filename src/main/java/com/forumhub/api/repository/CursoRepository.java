package com.forumhub.api.repository;

import com.forumhub.api.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    // Busca curso por nome (para validar entrada de tópicos)
    Optional<Curso> findByNome(String nome);
}
