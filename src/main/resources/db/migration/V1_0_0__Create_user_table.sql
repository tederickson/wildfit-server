CREATE TABLE user (
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,

    password    varchar(255) NOT NULL,
    uuid        varchar(40)  NOT NULL,
    email       varchar(100) NOT NULL,
    status      varchar(2)   NOT NULL,
    create_date date         NOT NULL,
    enabled     boolean      NOT NULL,

    KEY user_idx1 (email),
    KEY user_idx2 (uuid)
);


