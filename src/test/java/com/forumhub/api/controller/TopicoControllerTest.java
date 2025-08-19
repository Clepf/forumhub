package com.forumhub.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forumhub.api.dto.request.LoginRequest;
import com.forumhub.api.dto.request.TopicoRequest;
import com.forumhub.api.entity.Curso;
import com.forumhub.api.entity.Usuario;
import com.forumhub.api.repository.CursoRepository;
import com.forumhub.api.repository.TopicoRepository;
import com.forumhub.api.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Transactional
public class TopicoControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MockMvc mockMvc;
    private String jwtToken;
    private Usuario usuario;
    private Curso curso;

    @BeforeEach
    void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        topicoRepository.deleteAll();
        usuarioRepository.deleteAll();
        cursoRepository.deleteAll();

        usuario = Usuario.builder()
                .nome("Test User")
                .email("test@test.com")
                .senha(passwordEncoder.encode("123456"))
                .role("USER")
                .build();
        usuario = usuarioRepository.save(usuario);

        curso = Curso.builder()
                .nome("Java Básico")
                .categoria("Programação")
                .build();
        curso = cursoRepository.save(curso);

        LoginRequest loginRequest = new LoginRequest("test@test.com", "123456");
        MvcResult result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        jwtToken = "Bearer " + objectMapper.readTree(response).get("token").asText();
    }

    @Test
    void deveCriarTopicoComSucesso() throws Exception {
        TopicoRequest request = new TopicoRequest();
        request.setTitulo("Como usar Spring Boot?");
        request.setMensagem("Preciso de ajuda com Spring Boot");
        request.setStatus("ABERTO");
        request.setAutorId(usuario.getId());
        request.setCursoId(curso.getId());

        mockMvc.perform(post("/topicos")
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("Como usar Spring Boot?"))
                .andExpect(jsonPath("$.autorNome").value("Test User"))
                .andExpect(jsonPath("$.cursoNome").value("Java Básico"));
    }

    @Test
    void naoDeveCriarTopicoSemAutenticacao() throws Exception {
        TopicoRequest request = new TopicoRequest();
        request.setTitulo("Teste");
        request.setMensagem("Teste");
        request.setStatus("ABERTO");
        request.setAutorId(usuario.getId());
        request.setCursoId(curso.getId());

        mockMvc.perform(post("/topicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void naoDeveCriarTopicoDuplicado() throws Exception {
        TopicoRequest request = new TopicoRequest();
        request.setTitulo("Tópico Duplicado");
        request.setMensagem("Mensagem Duplicada");
        request.setStatus("ABERTO");
        request.setAutorId(usuario.getId());
        request.setCursoId(curso.getId());

        mockMvc.perform(post("/topicos")
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/topicos")
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    void deveListarTopicos() throws Exception {
        mockMvc.perform(get("/topicos")
                        .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void deveBuscarTopicoPorId() throws Exception {
        TopicoRequest request = new TopicoRequest();
        request.setTitulo("Busca por ID");
        request.setMensagem("Teste de busca");
        request.setStatus("ABERTO");
        request.setAutorId(usuario.getId());
        request.setCursoId(curso.getId());

        MvcResult result = mockMvc.perform(post("/topicos")
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        Long topicoId = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();

        mockMvc.perform(get("/topicos/" + topicoId)
                        .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(topicoId))
                .andExpect(jsonPath("$.titulo").value("Busca por ID"));
    }

    @Test
    void deveRetornar404ParaTopicoInexistente() throws Exception {
        mockMvc.perform(get("/topicos/999")
                        .header("Authorization", jwtToken))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Tópico não encontrado"));
    }
}