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
    nf_protein  float,
    nf_total_fat    float,
    nf_total_carbohydrate   float,
    nf_calories float,
    nf_sugars   float,
    nf_dietary_fiber    float,
    nf_calcium_mg   float,
    nf_iron_mg  float,
    nf_p    float,
    nf_potassium    float,
    nf_sodium   float,
    nf_vitamin_d_mcg    float,
    nf_added_sugars float,
    nf_cholesterol  float,
    nf_trans_fatty_acid float,
    nf_saturated_fat    float
);
