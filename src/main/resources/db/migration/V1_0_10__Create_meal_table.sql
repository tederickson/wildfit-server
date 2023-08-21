CREATE TABLE meal (
     id         BIGINT NOT NULL PRIMARY KEY,

     start_date date,
     end_date   date,
     uuid       varchar(255)  NOT NULL,

     KEY user_idx (uuid)
);

CREATE TABLE meal_seq (next_val bigint DEFAULT NULL);

INSERT INTO meal_seq VALUES(10);

CREATE TABLE meal_summary (
  id         BIGINT NOT NULL PRIMARY KEY,
  meal_id    BIGINT NOT NULL,
  recipe_id  BIGINT NOT NULL,
  cooked     bit(1) NOT NULL,
  plan_date  date,

  CONSTRAINT meal_fk FOREIGN KEY (meal_id) REFERENCES meal (id)
);

CREATE TABLE meal_summary_seq (next_val bigint DEFAULT NULL);

INSERT INTO meal_summary_seq VALUES(10);
