
-- Created Database 
CREATE DATABASE InventorySystem;
-- Use Database
USE InventorySystem;

-- Created table Product Table 
CREATE TABLE Product(
  Id VARCHAR(100) PRIMARY KEY,
  Name VARCHAR(50),
  Price FLOAT,
  Quantity INT
);

-- Use Table
SELECT *
FROM Product;

-- Trop Table
  DROP TABLE Product;
  
-- Search Product
SELECT * FROM Product
WHERE Product.Name = "Instand Noodle";


-- Adding Product
INSERT INTO Product(Id,Name,Price,Quantity) VALUES("3","Milk Banana",3,20);
