CREATE TABLE user_profile (
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,

    user_id bigint NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
    ON DELETE CASCADE,

    name    varchar(256) NOT NULL,
    age     int,
    gender  char(1),
    height  float,
    weight  float
);
