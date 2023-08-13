CREATE TABLE recipe (
  id BIGINT auto_increment PRIMARY KEY,
  email VARCHAR(255) NOT NULL,
  introduction VARCHAR (600),
  name VARCHAR(255) NOT NULL,
  prep_time_min INT NOT NULL,
  cook_time_min INT NOT NULL,
  season VARCHAR(10) NOT NULL,
  serving_unit VARCHAR(10) NOT NULL,
  serving_qty INT NOT NULL,
  created DATETIME NOT NULL,
  updated DATETIME,
  KEY RECIPE_season_idx (season)
);
CREATE TABLE recipe_seq (next_val BIGINT DEFAULT NULL);
INSERT INTO recipe_seq VALUES (10);
