CREATE TABLE shopping_list (
     id BIGINT NOT NULL PRIMARY KEY,
     uuid   varchar(255)  NOT NULL,

     KEY user_idx (uuid)
);

CREATE TABLE shopping_list_seq (next_val bigint DEFAULT NULL);

INSERT INTO shopping_list_seq VALUES(10);

CREATE TABLE shopping_list_item (
  id                    BIGINT NOT NULL PRIMARY KEY,
  shopping_list_id      BIGINT NOT NULL,
  food_name             varchar(255) DEFAULT NULL,
  serving_qty           float DEFAULT NULL,
  serving_unit          varchar(20) DEFAULT NULL,
  ingredient_type       varchar(20)  NOT NULL,

  CONSTRAINT shopping_list_item_fk FOREIGN KEY (shopping_list_id) REFERENCES shopping_list (id)
);

CREATE TABLE shopping_list_item_seq (next_val bigint DEFAULT NULL);

INSERT INTO shopping_list_item_seq VALUES(10);
