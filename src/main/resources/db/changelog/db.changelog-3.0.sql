--liquibase formatted sql

--changeset alexprokopiev:1
ALTER TABLE users
ADD COLUMN image VARCHAR(64);

--changeset alexprokopiev:2
ALTER TABLE users_aud
ADD COLUMN image VARCHAR(64);