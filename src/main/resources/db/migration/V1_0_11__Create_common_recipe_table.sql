CREATE TABLE common_recipe (
     id                     BIGINT NOT NULL PRIMARY KEY,
     type                   VARCHAR(20) NOT NULL,
     common_recipe_group_id BIGINT NOT NULL,

     KEY common_recipe_group_id_key (common_recipe_group_id),
     CONSTRAINT common_recipe_fk FOREIGN KEY (common_recipe_group_id) REFERENCES recipe_group (id)
);

CREATE TABLE common_recipe_seq (next_val bigint DEFAULT NULL);

INSERT INTO common_recipe_seq VALUES(10);

CREATE TABLE instruction (
  step_number int NOT NULL,
  text varchar(600) NOT NULL,
  common_recipe_join_id bigint NOT NULL,

  PRIMARY KEY (common_recipe_join_id),
  CONSTRAINT instruction1_fk FOREIGN KEY (common_recipe_join_id) REFERENCES common_recipe (id)
);

CREATE TABLE ingredient (
  description varchar(255) DEFAULT NULL,
  food_name varchar(255) DEFAULT NULL,
  ingredient_serving_qty float DEFAULT NULL,
  ingredient_serving_unit varchar(20) DEFAULT NULL,
  ingredient_type varchar(20)  NOT NULL,
  common_recipe_join_id bigint NOT NULL,

  PRIMARY KEY (common_recipe_join_id),
  KEY food_name_idx (food_name),
  CONSTRAINT ingredient_fk FOREIGN KEY (common_recipe_join_id) REFERENCES common_recipe (id)
);