CREATE TABLE Nutritionix_Nutrient_Field_Map (
    attr_id int NOT NULL PRIMARY KEY,

    usda_tag varchar(30) NOT NULL,
    name     varchar(100) NOT NULL,
    unit     varchar(10) NOT NULL,
    bulk_csv_field varchar(100) NOT NULL
);
