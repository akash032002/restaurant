CREATE DATABASE restaurant_db;

USE restaurant_db;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    password VARCHAR(50)
);

CREATE TABLE orders (
    order_id INT PRIMARY KEY,
    username VARCHAR(50),
    item VARCHAR(50),
    quantity INT,
    status VARCHAR(50)
);
