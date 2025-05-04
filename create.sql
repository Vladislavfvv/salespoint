
--
-- CREATE DATABASE salespoint;
-- -- переключиться на БД
-- \c salespoint;
-- CREATE SCHEMA IF NOT EXISTS salespointschema;
-- -- установка схемы по умолчанию
-- SET search_path TO salespointschema;

CREATE TABLE IF NOT EXISTS salespointschema.user_access
(
    id               bigserial primary key,
    user_login varchar(255) UNIQUE not null,
    user_password varchar(255) not null,
    full_name varchar(255) not null,
    user_role varchar(255) not null
);

CREATE TABLE IF NOT EXISTS salespointschema.payment_system
(
    id                  bigserial primary key,
    payment_system_name varchar(50) UNIQUE not null
);

CREATE TABLE IF NOT EXISTS salespointschema.acquiring_bank
(
    id               bigserial primary key,
    bic              varchar(9) UNIQUE not null,
    abbreviated_name varchar(255) not null
);

CREATE TABLE IF NOT EXISTS salespointschema.sales_point
(
    id                bigserial primary key,
    pos_name          varchar(255) not null,
    pos_address       varchar(255) not null,
    pos_inn           varchar(12) UNIQUE not null,
    acquiring_bank_id bigint REFERENCES salespointschema.acquiring_bank (id) ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS salespointschema.merchant_category_code
(
    id       bigserial primary key,
    mcc      varchar(4) UNIQUE not null,
    mcc_name varchar(255) not null
);
CREATE TABLE IF NOT EXISTS salespointschema.terminal
(
    id          bigserial primary key,
    terminal_id varchar(9) UNIQUE not null,
    mcc_id      bigint REFERENCES salespointschema.merchant_category_code (id) ON DELETE CASCADE
        ON UPDATE CASCADE,
    pos_id      bigint REFERENCES salespointschema.sales_point (id) ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS salespointschema.transaction_type
(
    id                    bigserial primary key,
    transaction_type_name varchar(255) UNIQUE not null,
    operator              varchar(1) not null
);

CREATE TABLE IF NOT EXISTS salespointschema.card
(
    id                         bigserial primary key,
    card_number                varchar(50) UNIQUE,
    expiration_date            date,
    holder_name                varchar(50),

    payment_system_id          bigint REFERENCES salespointschema.payment_system (id) ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS salespointschema.response_code
(
    id                bigserial primary key,
    error_code        varchar(2) UNIQUE not null,
    error_description varchar(255) not null,
    error_level       varchar(255) not null
);

CREATE TABLE IF NOT EXISTS salespointschema.transaction
(
    id                         bigserial primary key,
    transaction_date           date,
    sum                        decimal,

    transaction_type_id        bigint REFERENCES salespointschema.transaction_type (id) ON DELETE CASCADE
        ON UPDATE CASCADE,
    card_id                    bigint REFERENCES salespointschema.card (id) ON DELETE CASCADE
        ON UPDATE CASCADE,
    terminal_id                bigint REFERENCES salespointschema.terminal ON DELETE CASCADE
        ON UPDATE CASCADE,
    response_code_id           bigint REFERENCES salespointschema.response_code ON DELETE CASCADE
        ON UPDATE CASCADE,
    authorization_code         varchar(6)

);


