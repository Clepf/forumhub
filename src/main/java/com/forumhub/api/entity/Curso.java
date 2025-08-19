package com.forumhub.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "curso")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 100)
    private String categoria;
}
