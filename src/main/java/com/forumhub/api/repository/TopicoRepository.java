package com.forumhub.api.repository;

import com.forumhub.api.entity.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    // Busca tópicos por curso (nome do curso)
    Page<Topico> findAllByCursoNome(String cursoNome, Pageable pageable);

    // Busca tópicos por status
    Page<Topico> findAllByStatus(String status, Pageable pageable);

    // Busca tópicos cujo título contém certa palavra
    Page<Topico> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);
}
