DROP TABLE IF EXISTS products;
 
CREATE TABLE products (
  product_id INT PRIMARY KEY,
  product_name VARCHAR(250) NOT NULL,
  product_company VARCHAR(250) NOT NULL,
  product_category VARCHAR(250) NOT NULL
);

INSERT INTO products (product_id, product_name, product_company, product_category) VALUES
  (1, 'iPhoneXS', 'Apple', 'Smart Phone'),
  (2, 'iPhoneXR', 'Apple', 'Mobile Phones'),
  (3, 'Galaxy S10', 'Samsung', 'Mobile Phones'),
  (4, 'Latitude', 'Dell', 'Laptop');