CREATE TABLE instruction (
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,

    instruction_group_id bigint NOT NULL,
    FOREIGN KEY (instruction_group_id) REFERENCES instruction_group(id)
    ON DELETE CASCADE,

    step_number   int NOT NULL,
    text     varchar(600) NOT NULL
);
