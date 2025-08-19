package com.forumhub.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UsuarioRequest {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank @Email(message = "E-mail inválido")
    private String email;

    @NotBlank @Size(min = 6, message = "Senha deve ter ao menos 6 caracteres")
    private String senha;

    @NotBlank(message = "Role é obrigatório")
    private String role;
}
