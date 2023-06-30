CREATE TABLE recipe_ingredient (
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,

    recipe_id bigint NOT NULL,                  -- Recipe.id
    instruction_group_id bigint NOT NULL,       -- InstructionGroup.id

    food_name       varchar(100) NOT NULL,
    description     varchar(256) NOT NULL,
    brand_name      varchar(100),
    brand_name_item_name     varchar(100),
    serving_qty     float,
    serving_unit    varchar(10),
    ingredient_serving_qty  float NOT NULL,
    ingredient_serving_unit varchar(20) NOT NULL,
    serving_weight_grams    float,
    metric_qty  float,
    metric_uom  varchar(20),
    calories    float,
    total_fat   float,
    saturated_fat   float,
    cholesterol float,
    sodium      float,
    total_carbohydrate  float,
    dietary_fiber   float,
    sugars      float,
    protein     float,
    potassium   float,
    phosphorus  float,
    calcium     float,
    iron        float,
    vitamin_d   float,
    added_sugars        float,
    trans_fatty_acid    float,
    nix_brand_name  varchar(100),
    nix_brand_id    varchar(100),
    nix_item_id     varchar(100),
    photo_thumbnail varchar(100),

    KEY recipe_idx1 (recipe_id),
    KEY food_name_idx (food_name)
);
