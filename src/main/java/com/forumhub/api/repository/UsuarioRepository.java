package com.forumhub.api.repository;

import com.forumhub.api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Busca usuário por e-mail (para autenticação)
    Optional<Usuario> findByEmail(String email);
}
