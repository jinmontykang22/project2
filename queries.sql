-- grabs the orders from a specific time frame
SELECT * FROM entries WHERE order_date BETWEEN 'start_date_here' AND 'end_date_here';

-- grabs all product_categories
SELECT DISTINCT category FROM Products;

-- grabs all products without milk in it
SELECT p.*
FROM products p
WHERE NOT EXISTS (
    SELECT 1
    FROM product_ingredients pi
    JOIN ingredients i ON pi.ingredient_id = i.id
    WHERE pi.product_id = p.id
      AND i.name = 'milk'
);


-- grabs the most recent order
SELECT * FROM Orders ORDER BY order_id DESC LIMIT 1;

-- calculates the sum of the revenue of the day
SELECT SUM(total_price) FROM Orders WHERE order_date = 'insert_date_here';

-- grabs all active products in an category
select * FROM products WHERE category = 'insert_category_here' AND is_active = true;

-- grabs all inactive products
SELECT * FROM products WHERE is_active = false;

-- grabs all out of stock ingredients
SELECT * FROM ingredients WHERE quantity_in_stock = 0;

-- calculates the sum of all the tips for the day
SELECT SUM(tip) FROM Orders WHERE order_date = 'insert_date_here';
