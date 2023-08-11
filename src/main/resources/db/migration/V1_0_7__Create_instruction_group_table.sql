CREATE TABLE instruction_group (
    id bigint AUTO_INCREMENT PRIMARY KEY,

    recipe_id bigint NOT NULL,  -- Recipe.id

    instruction_group_number    int NOT NULL,
    name                        varchar(255),

    KEY recipe_idx1 (recipe_id)
);

CREATE TABLE recipe_group_1 (
     id                  BIGINT PRIMARY KEY,
     name                VARCHAR(255),
     recipe_group_number INTEGER NOT NULL,
     recipe_group_id     BIGINT NOT NULL,

     CONSTRAINT recipe_group_1_fk FOREIGN KEY (recipe_group_id) REFERENCES recipe_1 (id)
);

CREATE TABLE recipe_group_1_seq (next_val bigint DEFAULT NULL);

INSERT INTO recipe_group_1_seq VALUES(10);
