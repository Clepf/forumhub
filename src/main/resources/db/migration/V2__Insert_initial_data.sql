-- V2__Insert_initial_data.sql

INSERT INTO curso (nome, categoria) VALUES
  ('Spring Boot', 'BACKEND'),
  ('Java Básico', 'BACKEND'),
  ('JavaScript', 'FRONTEND');

INSERT INTO usuario (nome, email, senha, role) VALUES
  ('Alice Santos', 'alice@forum.com', '$2a$10$hashbcrypt', 'USER'),
  ('Carlos Meireles', 'carlos@forum.com', '$2a$10$hashbcrypt', 'MODERATOR');
