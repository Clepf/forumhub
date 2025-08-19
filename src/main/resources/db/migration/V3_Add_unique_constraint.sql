-- Adiciona constraint única para prevenir tópicos duplicados (mesmo título e mensagem)
ALTER TABLE topico
ADD CONSTRAINT uk_topico_titulo_mensagem
UNIQUE (titulo, mensagem);