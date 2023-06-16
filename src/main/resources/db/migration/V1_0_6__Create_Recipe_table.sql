CREATE TABLE Recipe (
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,

      created_by bigint NOT NULL,
      introduction varchar(600) ,
      name varchar(256) NOT NULL,
      prep_time_min int NOT NULL,
      cook_time_min int NOT NULL,
      season varchar(10) NOT NULL,
      serving_unit varchar(10) NOT NULL,
      serving_qty int NOT NULL,
      instructions varchar(2000) NOT NULL,
      created datetime NOT NULL,
      updated datetime,

      KEY season_idx (season)
);
