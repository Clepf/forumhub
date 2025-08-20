package com.forumhub.api.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RespostaResponse {

    private Long id;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private Boolean solucao;
    private Long autorId;
    private String autorNome;
    private Long topicoId;
}
