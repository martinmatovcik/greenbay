ALTER TABLE products
    CHANGE COLUMN deleted sold BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE products
    ADD COLUMN user_id BIGINT NOT NULL DEFAULT 0,
    ADD CONSTRAINT users_fk FOREIGN KEY (user_id) REFERENCES users (id);

CREATE TABLE IF NOT EXISTS bids
(
    id         BIGINT   NOT NULL AUTO_INCREMENT,
    value      INT      NOT NULL,
    created_at DATETIME NOT NULL,
    product_id BIGINT,
    user_id    BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES products (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

