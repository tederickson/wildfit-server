CREATE TABLE recipe_ingredient (
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,

    recipe_id bigint NOT NULL,
    FOREIGN KEY (recipe_id) REFERENCES user(id)
    ON DELETE CASCADE,

    food_name       varchar(256) NOT NULL,
    brand_name      varchar(256),
    nix_item_id     varchar(256),
    nix_brand_id    varchar(256),
    brand_name_item_name    varchar(256),
    serving_qty             int NOT NULL,
    serving_unit            varchar(10) NOT NULL,
    serving_weight_grams    int,
    nf_calories             int,
    nf_total_fat            int,
    nf_saturated_fat        int,
    nf_cholesterol          int,
    nf_sodium               int,
    nf_total_carbohydrate   int,
    nf_dietary_fiber        int,
    nf_sugars               int,
    nf_protein              int,
    nf_potassium            int,
    nf_p                    int
);
