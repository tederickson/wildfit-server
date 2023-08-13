CREATE TABLE recipe_group_1 (
     id                  BIGINT PRIMARY KEY,
     name                VARCHAR(255),
     recipe_group_number INTEGER NOT NULL,
     recipe_id           BIGINT NOT NULL,

     CONSTRAINT recipe_id_fk FOREIGN KEY (recipe_id) REFERENCES recipe_1 (id)
);

CREATE TABLE recipe_group_1_seq (next_val bigint DEFAULT NULL);

INSERT INTO recipe_group_1_seq VALUES(10);
