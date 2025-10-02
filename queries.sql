CREATE TABLE Items (
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

CREATE TABLE Orders (
    order_id        SERIAL PRIMARY KEY,
    order_time      TIMESTAMP NOT NULL,
    status          VARCHAR(20) NOT NULL CHECK (status IN ('Completed', 'Pending')),
    total_price     NUMERIC(10,2) NOT NULL,
    tip             NUMERIC(10,2) DEFAULT 0.00,
    special_notes   VARCHAR(255)
);




-- grabs the orders from a specific time frame and sort them
SELECT *
FROM Orders
WHERE order_time BETWEEN '2025-01-01 10:00:00'
                    AND '2025-01-01 15:00:00'
ORDER BY order_time DESC;

-- grabs all hot beverages
SELECT * FROM Products WHERE temperature = 'hot';

-- grabs all products without milk in it
SELECT * FROM products WHERE milk = 0;

-- grabs the most recent order
SELECT * FROM Orders ORDER BY order_time LIMIT 1;

-- calculates the sum of the revenue of the day
SELECT SUM(total_price)
FROM Orders
WHERE DATE(order_time) = '2025-01-01';

-- grabs all products with no sugar
SELECT * FROM products WHERE sugar = 0;

-- grabs all nonmanagers from staff
SELECT * FROM staff WHERE role != 'Manager';

-- grabs low-stock inventory items
SELECT name FROM inventory WHERE units_remaining < 10;

-- grabs items from a specific order
SELECT *
FROM items
WHERE order_id = 2;

-- calculates yearly employment costs
SELECT SUM(salary) FROM staff;

-- This query retrieves the number of orders placed each week.
-- It groups the orders by the week they were placed and counts the number of orders in each week.
-- The results are ordered by the week start date.
SELECT 
    DATE_TRUNC('week', order_time) AS week_start,
    COUNT(order_id) AS order_count
FROM orders
GROUP BY week_start
ORDER BY week_start;

-- Query to find the number of orders placed each hour of the day
-- This query groups the orders by the hour they were placed and counts the number of orders in each hour.
-- The results are ordered by the hour of the day.  
SELECT 
    DATE_PART('hour', order_time) AS hour_of_day,
    COUNT(order_id) AS order_count,
    SUM(total_price) AS total_sales
FROM orders
GROUP BY hour_of_day
ORDER BY hour_of_day;

-- Query to find the top 10 sales days
-- This query groups the orders by the date they were placed and sums the total sales for each day.
-- The results are ordered by total sales in descending order, and only the top 10
SELECT 
    DATE(order_time) AS order_day,
    SUM(total_price) AS total_sales
FROM orders
GROUP BY order_day
ORDER BY total_sales DESC
LIMIT 10;