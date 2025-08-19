-- Criação da tabela Resposta
CREATE TABLE resposta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mensagem TEXT NOT NULL,
    data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    solucao BOOLEAN NOT NULL DEFAULT FALSE,
    topico_id BIGINT NOT NULL,
    autor_id BIGINT NOT NULL,
    CONSTRAINT fk_resposta_topico FOREIGN KEY (topico_id)
        REFERENCES topico(id) ON DELETE CASCADE,
    CONSTRAINT fk_resposta_usuario FOREIGN KEY (autor_id)
        REFERENCES usuario(id) ON DELETE CASCADE
);