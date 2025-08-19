-- Criação da tabela Usuario
CREATE TABLE usuario (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  email VARCHAR(150) NOT NULL UNIQUE,
  senha VARCHAR(255) NOT NULL,
  role VARCHAR(50) NOT NULL
);

-- Criação da tabela Curso
CREATE TABLE curso (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  categoria VARCHAR(100) NOT NULL
);

-- Criação da tabela Topico
CREATE TABLE topico (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  titulo VARCHAR(200) NOT NULL,
  mensagem TEXT NOT NULL,
  data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  status VARCHAR(20) NOT NULL,
  autor_id BIGINT NOT NULL,
  curso_id BIGINT NOT NULL,
  CONSTRAINT fk_topico_usuario FOREIGN KEY (autor_id)
    REFERENCES usuario(id) ON DELETE CASCADE,
  CONSTRAINT fk_topico_curso FOREIGN KEY (curso_id)
    REFERENCES curso(id) ON DELETE RESTRICT
);
