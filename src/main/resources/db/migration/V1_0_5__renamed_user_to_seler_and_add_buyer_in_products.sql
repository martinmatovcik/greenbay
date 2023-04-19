ALTER TABLE products
    CHANGE COLUMN user_id seller_id BIGINT NOT NULL;

ALTER TABLE products
    ADD COLUMN buyer_id BIGINT DEFAULT NULL,
    ADD CONSTRAINT buyer_fk FOREIGN KEY (buyer_id) REFERENCES users (id);



