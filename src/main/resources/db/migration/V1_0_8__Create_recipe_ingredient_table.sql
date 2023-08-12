CREATE TABLE recipe_ingredient (
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,

    recipe_id bigint NOT NULL,                  -- Recipe1.id
    instruction_group_id bigint NOT NULL,       -- InstructionGroup.id

    ingredient_type varchar(2) NOT NULL,

    food_name       varchar(100) NOT NULL,
    description     varchar(255) NOT NULL,

    ingredient_serving_qty  float NOT NULL,
    ingredient_serving_unit varchar(50),

    KEY recipe_idx1 (recipe_id),
    KEY food_name_idx (food_name)
);
