package com.forumhub.api.dto.response;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UsuarioResponse {

    private Long id;
    private String nome;
    private String email;
    private String role;
}
