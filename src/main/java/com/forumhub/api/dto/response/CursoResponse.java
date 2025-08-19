package com.forumhub.api.dto.response;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CursoResponse {

    private Long id;
    private String nome;
    private String categoria;
}
