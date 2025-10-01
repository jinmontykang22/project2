-- grabs the orders from a specific time frame and sort them
SELECT * FROM Orders WHERE day BETWEEN '2025-01-01' AND '2025-01-01' AND time BETWEEN '10:00:00' AND '15:00:00' ORDER BY day DESC, time DESC;

-- grabs all hot beverages
SELECT * FROM Products WHERE temperature = 'hot';

-- grabs all products without milk in it
SELECT * FROM products WHERE milk = 0;

-- grabs the most recent order
SELECT * FROM Orders ORDER BY day DESC, time DESC LIMIT 1;

-- calculates the sum of the revenue of the day
SELECT SUM(price) FROM Orders WHERE day = '2025-01-01';

-- grabs all products with no sugar
SELECT * FROM products WHERE sugar = 0;

-- grabs all nonmanagers from staff
SELECT * FROM staff WHERE role != 'Manager';

-- grabs all out of stock inventory items
SELECT name FROM inventory WHERE units_remaining = 0;

-- grabs all out of stock ingredients
SELECT ing_name FROM ingredients WHERE numservings = 0;

-- calculates yearly employment costs
SELECT SUM(salary) FROM staff;
