# URL Shortener

API REST de encurtamento de URLs com expiração automática, métricas de cliques e cache com Redis.

## Tecnologias

- Java 21
- Spring Boot 4.0.5
- Spring Data JPA
- PostgreSQL 16
- Redis 7
- Flyway
- Docker Compose
- Testcontainers
- GitHub Actions (CI)

## Como rodar

1. Clone o repositório: git clone https://github.com/xxxgo-stak/url-shortener.git cd url-shortener
2. Suba os serviços com Docker Compose: docker-compose up -d
3. Rode a aplicação: ./mvnw spring-boot:run


A API estará disponível em `http://localhost:8080`.

## Endpoints

### POST /api/urls
Encurta uma URL. O link expira automaticamente em 7 dias.

**Request:**
```json
{
    "url": "https://www.google.com"
}
```

**Response (201 Created):**
```json
{
    "code": "47aa5e12",
    "shortUrl": "http://localhost:8080/api/urls/47aa5e12"
}
```

### GET /api/urls/{code}
Redireciona para a URL original (302 Found).

### GET /api/urls/{code}/stats
Retorna métricas do link encurtado.

**Response:**
```json
{
    "code": "47aa5e12",
    "originalUrl": "https://www.google.com",
    "clickCount": 5,
    "createdAt": "2026-04-06T01:14:56",
    "expiresAt": "2026-04-13T01:14:56"
}
```

## Decisões técnicas

- **UUID truncado para code:** os primeiros 8 caracteres de um UUID garantem unicidade suficiente para o escopo do projeto, com unique constraint no banco como fallback.
- **Flyway para migrations:** versionamento do schema do banco, garantindo reprodutibilidade em qualquer ambiente.
- **Redis para cache:** evita queries repetidas ao banco em links com alto volume de acessos.
- **Testcontainers:** testes de integração rodam contra um PostgreSQL real em container, sem mocks de banco.
- **Variáveis de ambiente:** credenciais configuráveis via variáveis de ambiente com valores padrão para desenvolvimento local.

## Rodando os testes
   ./mvnw clean verify
   Os testes de integração usam Testcontainers, então o Docker precisa estar rodando.