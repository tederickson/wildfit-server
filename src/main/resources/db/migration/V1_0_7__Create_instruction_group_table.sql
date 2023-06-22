CREATE TABLE instruction_group (
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,

    recipe_id bigint NOT NULL,  -- Recipe.id

    instruction_group_number    int NOT NULL,
    name                        varchar(256)
);
