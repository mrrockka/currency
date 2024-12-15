--liquibase formatted sql

--changeset step:3
CREATE TABLE IF NOT EXISTS currency (
    code varchar(3) PRIMARY KEY,
    exchange_rates jsonb
);
