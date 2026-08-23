
-- Created a User Database 
CREATE DATABASE UserInformation;

-- Use UserDatabase
USE UserInformation;

--  Table for Information User 
CREATE TABLE Information(
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    fullname VARCHAR(50) NOT NULL,
    telephone VARCHAR(50) NOT NULL UNIQUE,
    location VARCHAR(50) NOT NULL,
	role VARCHAR(30) NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- Delete Table 1
DROP TABLE Information;
-- SELETE Table 1
SELECT * FROM Information;

INSERT INTO Information(email , password , fullname , telephone , location , role) VALUES
('Admin123@gmail.com' , 'Admin123' , 'Thou rayuth' , '0964723309' , 'Kompost provide' , 'Admin');

-- This table is for Balance
CREATE TABLE UserBalance(
    id INT PRIMARY KEY,
    FOREIGN KEY(id) REFERENCES Information(id) ON DELETE CASCADE,
    balance DECIMAL(10,2) NOT NULL DEFAULT 0.00
); 
-- SELECT Table 2 
SELECT * FROM UserBalance;
-- Delete Table 2
DROP TABLE UserBalance;


-- This Table for Order of User
CREATE TABLE OrderUser(
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    FOREIGN KEY(user_id) REFERENCES Information(id) ON DELETE CASCADE,
    nameProduct VARCHAR(50) NOT NULL,
    productPrice DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    quantity INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
DROP TABLE OrderUser;
SELECT * FROM OrderUser;


