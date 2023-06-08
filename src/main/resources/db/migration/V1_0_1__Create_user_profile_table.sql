CREATE TABLE user_profile (
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,

    user_id bigint,
    CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES user(id)
    ON DELETE CASCADE,

    name    varchar(256),
    age     int,
    gender  char(1),
    height  float,
    weight  float
);
