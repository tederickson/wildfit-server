CREATE TABLE verification_token (
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,

    user_id bigint NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
    ON DELETE CASCADE,

    expiry_date date    NOT NULL,
    token    varchar(30),

    KEY token_idx1(token)
);
