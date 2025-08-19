package com.forumhub.api.service;

import com.forumhub.api.dto.request.UsuarioRequest;
import com.forumhub.api.dto.response.UsuarioResponse;
import com.forumhub.api.entity.Usuario;
import com.forumhub.api.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepo,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepo = usuarioRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UsuarioResponse criar(UsuarioRequest req) {
        if (usuarioRepo.findByEmail(req.getEmail()).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }
        Usuario usuario = Usuario.builder()
                .nome(req.getNome())
                .email(req.getEmail())
                .senha(passwordEncoder.encode(req.getSenha()))
                .role(req.getRole())
                .build();
        Usuario salvo = usuarioRepo.save(usuario);
        return new UsuarioResponse(salvo.getId(), salvo.getNome(), salvo.getEmail(), salvo.getRole());
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listar() {
        return usuarioRepo.findAll().stream()
                .map(u -> new UsuarioResponse(u.getId(), u.getNome(), u.getEmail(), u.getRole()))
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioResponse buscarPorId(Long id) {
        Usuario u = usuarioRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));
        return new UsuarioResponse(u.getId(), u.getNome(), u.getEmail(), u.getRole());
    }
}
