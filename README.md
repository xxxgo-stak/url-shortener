# URL Shortener

Encurtador de URLs construído com Spring Boot.

## Tecnologias

- Java 21
- Spring Boot 4.0.5
- Spring Data JPA
- PostgreSQL 16
- Flyway
- Docker

## Como rodar

1. Suba o PostgreSQL com Docker:
2. Configure as variáveis de ambiente (opcional, já tem valores padrão):

## Endpoints

**POST /api/urls** — Encurta uma URL

**GET /api/urls/{code}** — Redireciona para a URL original

## Status

Projeto em desenvolvimento.