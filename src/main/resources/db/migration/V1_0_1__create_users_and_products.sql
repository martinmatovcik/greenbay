CREATE SCHEMA IF NOT EXISTS greenbay_v2;
CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255),
    email    VARCHAR(255),
    password VARCHAR(255),
    role     VARCHAR(255),
    balance  INT    NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS products
(
    id             BIGINT NOT NULL AUTO_INCREMENT,
    name           VARCHAR(255),
    description    VARCHAR(255),
    photo_url      VARCHAR(255),
    starting_price VARCHAR(255),
    purchase_price INT    NOT NULL,
    PRIMARY KEY (id)
);