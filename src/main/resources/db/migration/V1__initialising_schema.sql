CREATE TABLE friend (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255)
);

CREATE TABLE expense (
    id INT PRIMARY KEY AUTO_INCREMENT,
    amount DOUBLE,
    description VARCHAR(255),
    date TIMESTAMP,
    friend_id INT,
    FOREIGN KEY (friend_id) REFERENCES friend (id) ON DELETE CASCADE
);
