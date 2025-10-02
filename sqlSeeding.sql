-- run in psql terminal with \i 'C:/Users/monty/CSCE/CSCE_331/project2_personal/project2/tables/sqlSeeding.sql'
-- !you should replace the copy lines and the file paths with your own device file paths!
-- this file will create tables in the db and seed them with data from the csv files
CREATE TABLE ingredients(
    ing_id INT PRIMARY KEY,
    ing_name TEXT,
    numServings INT
);

\copy ingredients from 'C:\Users\monty\CSCE\CSCE_331\project2_personal\project2\tables\ingredients.csv' CSV HEADER

CREATE TABLE inventory(
    inv_item_id INT PRIMARY KEY,
    name TEXT, -- changed from 'name'
    units_remaining INT,
    numServings INT
);

\copy inventory from 'C:\Users\monty\CSCE\CSCE_331\project2_personal\project2\tables\inventory.csv' CSV HEADER

CREATE TABLE items (
    item_id SERIAL PRIMARY KEY,      
    order_id INT NOT NULL,           
    product_id INT NOT NULL,        
    quantity INT NOT NULL CHECK (quantity > 0),
    size VARCHAR(20) NOT NULL CHECK (size IN ('Small','Medium','Large','Bucees_Large')),
    sugar_level VARCHAR(5) NOT NULL CHECK (sugar_level IN ('0','50','75','100')),
    ice_level VARCHAR(5) NOT NULL CHECK (ice_level IN ('0','50','75','100')),
    toppings TEXT,                  
    price NUMERIC(10,2) NOT NULL CHECK (price >= 0),

    FOREIGN KEY (order_id) REFERENCES Orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES Products(product_id) ON DELETE RESTRICT
);

\copy items from 'C:\Users\monty\CSCE\CSCE_331\project2_personal\project2\tables\items.csv' CSV HEADER

CREATE TABLE orders (
    order_id        SERIAL PRIMARY KEY,
    order_time      TIMESTAMP NOT NULL,
    status          VARCHAR(20) NOT NULL CHECK (status IN ('Completed', 'Pending')),
    total_price     NUMERIC(10,2) NOT NULL,
    tip             NUMERIC(10,2) DEFAULT 0.00,
    special_notes   VARCHAR(255)
);

\copy orders from 'C:\Users\monty\CSCE\CSCE_331\project2_personal\project2\tables\orders.csv' CSV HEADER

CREATE TABLE products(
    product_id INT PRIMARY KEY,
    product_name TEXT,
    price DECIMAL,
    temperature TEXT,
    flavor INT,
    flavor_2 INT,
    flavor_3 INT,
    milk DECIMAL,
    cream INT,
    sugar INT
);

\copy products from 'C:\Users\monty\CSCE\CSCE_331\project2_personal\project2\tables\products.csv' CSV HEADER

CREATE TABLE staff(
    staff_id TEXT PRIMARY KEY,
    name TEXT,
    role TEXT,
    salary INT,
    hours_worked INT
);

\copy staff from 'C:\Users\monty\CSCE\CSCE_331\project2_personal\project2\tables\staff.csv' CSV HEADER


-- ingredients (ing_id INT PKEY, ing_name TEXT, numServings INT),
--     inventory (inv_item_id INT PKEY, name TEXT, units_remaining INT, numServings INT),
--     items (order_id INT, product_id INT, item_id TEXT PKEY, size TEXT, sugar INT, ice INT, extra_milk INT), --typo, pkey is not order_id; it is item_id
--     orders (order_id INT PKEY, day DATE, time TIME, price DECIMAL),
--     products (product_id INT PKEY, product_name TEXT, price DECIMAL, temperature TEXT, flavor INT, flavor_2 INT, flavor_3 INT, milk DECIMAL, cream INT, sugar INT),
--     staff (name TEXT, id TEXT PKEY, role TEXT, salary INT, hours INT)
