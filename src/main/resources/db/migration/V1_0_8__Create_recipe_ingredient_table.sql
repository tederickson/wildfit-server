CREATE TABLE recipe_ingredient (
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,

    recipe_id bigint NOT NULL,                  -- Recipe.id
    instruction_group_id bigint NOT NULL,       -- InstructionGroup.id

    food_name       varchar(256) NOT NULL,
    brand_name      varchar(256),
    brand_name_item_name     varchar(256),
    serving_qty    integer,
    serving_unit    varchar(10),
    ingredient_serving_qty  integer NOT NULL,
    ingredient_serving_unit varchar(16) NOT NULL,
    serving_weight_grams    integer,
    metric_qty  integer,
    metric_uom varchar(16),
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
    added_sugars    float,
    trans_fatty_acid    float,
    nix_brand_name  varchar(256),
    nix_brand_id    varchar(256),
    nix_item_id     varchar(256),
    photo_thumbnail varchar(256),

    KEY recipe_idx1 (recipe_id)
);
