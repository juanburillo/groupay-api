CREATE TABLE friend (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255)
);

CREATE TABLE expense (
    id INT PRIMARY KEY AUTO_INCREMENT,
    amount DOUBLE,
    description VARCHAR(255),
    `date` TIMESTAMP,
    friend_id INT,
    FOREIGN KEY (friend_id) REFERENCES friend (id) ON DELETE CASCADE
);

INSERT INTO friend (name) VALUES ("Juan");
INSERT INTO friend (name) VALUES ("María");
INSERT INTO friend (name) VALUES ("Belén");

INSERT INTO expense (amount, description, date, friend_id) VALUES (10.0, "Description 1", null, 1);
INSERT INTO expense (amount, description, date, friend_id) VALUES (20.0, "Description 2", null, 2);
INSERT INTO expense (amount, description, date, friend_id) VALUES (30.0, "Description 3", null, 3);