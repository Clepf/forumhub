# 💬 ForumHub - API REST para Fórum de Discussões 💬

## 📋 Índice
- [Sobre o Projeto](#-sobre-o-projeto)
- [Status do Projeto](#-status-do-projeto)
- [Funcionalidades](#-funcionalidades)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Pré-requisitos](#-pré-requisitos)
- [Como Executar](#-como-executar)
- [Pessoas Desenvolvedoras](#-pessoas-desenvolvedoras)

***

## 🎯 Sobre o Projeto

ForumHub é uma API REST desenvolvida como parte do Challenge Alura + Oracle ONE. O projeto replica o funcionamento de um fórum de discussões no nível de back-end, permitindo que usuários criem, visualizem, atualizem e excluam tópicos de discussão.

Inspirado no fórum da Alura, onde alunos tiram dúvidas sobre cursos e projetos, o ForumHub implementa todas as operações CRUD com autenticação robusta e regras de negócio bem definidas.

### 🎨 Características do Projeto:
- ✅ API REST completa seguindo padrões REST
- ✅ Autenticação e autorização com JWT
- ✅ Validações de regras de negócio
- ✅ Persistência com MySQL e Flyway
- ✅ Spring Security implementado
- ✅ Documentação de endpoints
- ✅ Tratamento de erros padronizado

***

## ⏱️ Status do Projeto
✅ Projeto Finalizado ✅

***

## ⚡ Funcionalidades

### 🔐 Sistema de Autenticação
- Login com geração de token JWT
- Controle de acesso a endpoints protegidos
- Validação de token em todas as requisições
- Expiração automática de tokens

### 📝 Gerenciamento de Tópicos
- **Criar Tópico**: Cadastro de novos tópicos com validações
- **Listar Tópicos**: Visualização paginada com ordenação
- **Detalhar Tópico**: Consulta individual por ID
- **Atualizar Tópico**: Edição de tópicos existentes
- **Excluir Tópico**: Remoção lógica/física de tópicos

### 🛡️ Validações Implementadas
- Campos obrigatórios validados
- Prevenção de tópicos duplicados (título + mensagem)
- Verificação de existência antes de operações
- Tratamento de erros com status HTTP apropriados

### 📊 Funcionalidades Extras
- Paginação e ordenação de resultados
- Busca por critérios específicos (curso, ano)
- Listagem dos 10 primeiros resultados ordenados por data
- Logs detalhados de operações

***

## 🛠 Tecnologias Utilizadas

### Backend & Framework:
- **Java 17+** - Linguagem principal
- **Spring Boot 3** - Framework principal
- **Spring Web** - API REST
- **Spring Data JPA** - Persistência de dados
- **Spring Security** - Autenticação e autorização
- **Spring Boot DevTools** - Desenvolvimento

### Banco de Dados:
- **MySQL 8+** - Banco de dados principal
- **Flyway Migration** - Controle de versão do banco
- **Hibernate** - ORM

### Segurança & Autenticação:
- **JWT (JSON Web Token)** - Tokens de autenticação
- **BCrypt** - Hash de senhas
- **HMAC256** - Algoritmo de assinatura

### Validação & Utilitários:
- **Bean Validation** - Validações automáticas
- **Lombok** - Redução de código boilerplate
- **Jackson** - Processamento JSON

### Ferramentas & Build:
- **Maven** - Gerenciamento de dependências
- **Postman/Insomnia** - Testes de API
- **MySQL Workbench** - Administração do banco

***

## 📋 Pré-requisitos

Certifique-se de ter instalado:
- ☕ **Java JDK 17+**
- 🗃️ **MySQL 8+**
- 📦 **Maven 3.8+**
- 💻 **IDE** (IntelliJ IDEA recomendado)
- 🔧 **Postman/Insomnia** (para testes)

***

## 🚀 Como Executar

### 1. Clone o repositório:
```bash
git clone https://github.com/Clepf/forumhub.git
cd forumhub
```

### 2. Configure o banco de dados MySQL:
```sql
CREATE DATABASE forumhub;
CREATE USER 'forumhub_user'@'localhost' IDENTIFIED BY 'sua_senha';
GRANT ALL PRIVILEGES ON forumhub.* TO 'forumhub_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Configure application.properties:
```properties
# Banco de dados
spring.datasource.url=jdbc:mysql://localhost:3306/forumhub
spring.datasource.username=forumhub_user
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true

# Flyway
spring.flyway.enabled=true

# JWT
jwt.secret=minha_chave_secreta_super_segura
jwt.expiration=86400000
```

### 4. Execute o projeto:
```bash
# Via Maven
mvn spring-boot:run

# Ou via IDE
# Execute a classe ForumhubApplication
```

### 5. Acesse a API:
```
Base URL: http://localhost:8080
Login: POST /login
Tópicos: /topicos
```

***


## 👥 Pessoas Desenvolvedoras

| [<img loading="lazy" src="https://avatars.githubusercontent.com/u/88713149?s=400&u=4104bd7a1fb2143ecf5d1470b0c829bc5898c250&v=4" width=115><br><sub>Clepf</sub>](https://github.com/Clepf) |
| :---: |

***

> Este projeto foi desenvolvido como parte do Challenge Oracle Next Education (ONE) e demonstra implementação completa de uma API REST com Spring Boot, incluindo autenticação JWT, validações robustas e arquitetura escalável.
