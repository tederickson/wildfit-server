CREATE TABLE recipe_ingredient (
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,

    recipe_id bigint NOT NULL,
    FOREIGN KEY (recipe_id) REFERENCES user(id)
    ON DELETE CASCADE,

    food_name       varchar(256) NOT NULL,
    brand_name      varchar(256) NOT NULL,
    nix_item_id     varchar(256) NOT NULL,
    nix_brand_id    varchar(256) NOT NULL,
    brand_name_item_name    varchar(256) NOT NULL,
    serving_qty     int NOT NULL,
    serving_unit    varchar(10) NOT NULL,
    serving_weight_grams    int NOT NULL,
    calories        float NOT NULL
);
