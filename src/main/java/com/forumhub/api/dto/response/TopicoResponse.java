package com.forumhub.api.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TopicoResponse {

    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private String status;
    private Long autorId;
    private String autorNome;
    private Long cursoId;
    private String cursoNome;
}
