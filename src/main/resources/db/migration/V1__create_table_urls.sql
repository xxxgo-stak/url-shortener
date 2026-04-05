CREATE TABLE urls (
    id BIGSERIAL primary key,
    code VARCHAR(8) not null,
    original_url VARCHAR (2000) not null,
    created_at TIMESTAMP not null
);