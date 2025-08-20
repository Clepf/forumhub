package com.forumhub.api.repository;

import com.forumhub.api.entity.Resposta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {
    List<Resposta> findAllByTopicoId(Long topicoId);
}
