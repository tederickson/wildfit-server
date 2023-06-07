CREATE TABLE user (
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,

    user_name varchar(20) NOT NULL,
    password varchar(256) NOT NULL,
    email varchar(100),

    KEY user_idx1 (user_name),
    KEY user_idx2 (email)
);


