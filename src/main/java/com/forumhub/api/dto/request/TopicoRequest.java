package com.forumhub.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TopicoRequest {

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 200, message = "Máximo de 200 caracteres")
    private String titulo;

    @NotBlank(message = "Mensagem é obrigatória")
    private String mensagem;

    @NotBlank(message = "Status é obrigatório")
    private String status;

    @NotNull(message = "ID do autor é obrigatório")
    private Long autorId;

    @NotNull(message = "ID do curso é obrigatório")
    private Long cursoId;
}
