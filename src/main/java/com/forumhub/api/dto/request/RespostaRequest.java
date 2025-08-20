package com.forumhub.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RespostaRequest {

    @NotBlank
    private String mensagem;

    @NotNull
    private Long autorId;

    @NotNull
    private Long topicoId;

    @NotNull
    private Boolean solucao;
}
