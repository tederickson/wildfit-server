CREATE TABLE recipe_group (
     id                  BIGINT PRIMARY KEY,
     name                VARCHAR(255),
     recipe_group_number INTEGER NOT NULL,
     recipe_id           BIGINT NOT NULL,

     CONSTRAINT recipe_id_fk FOREIGN KEY (recipe_id) REFERENCES recipe (id)
);

CREATE TABLE recipe_group_seq (next_val bigint DEFAULT NULL);

INSERT INTO recipe_group_seq VALUES(10);
