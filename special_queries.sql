-- This query retrieves the number of orders placed each week.
-- It groups the orders by the week they were placed and counts the number of orders in each week.
-- The results are ordered by the week start date.
SELECT 
    DATE_TRUNC('week', order_date) AS week_start,
    COUNT(order_id) AS order_count
FROM orders
GROUP BY week_start
ORDER BY week_start;

-- Query to find the number of orders placed each hour of the day
-- This query groups the orders by the hour they were placed and counts the number of orders in each hour.
-- The results are ordered by the hour of the day.  
SELECT 
    DATE_PART('hour', order_date) AS hour_of_day,
    COUNT(order_id) AS order_count,
    SUM(total_price) AS total_sales
FROM orders
GROUP BY hour_of_day
ORDER BY hour_of_day;

-- Query to find the top 10 sales days
-- This query groups the orders by the date they were placed and sums the total sales for each day.
-- The results are ordered by total sales in descending order, and only the top 10
SELECT 
    DATE(order_date) AS order_day,
    SUM(total_price) AS total_sales
FROM orders
GROUP BY order_day
ORDER BY total_sales DESC
LIMIT 10;

-- Query to find the products with the highest number of ingredients
-- This query joins the products table with the product_ingredients table to count the number of ingredients for each product.
-- The results are grouped by product name and ordered by the ingredient count in descending order.
SELECT 
    p.name AS product_name,
    COUNT(pi.ingredient_id) AS ingredient_count
FROM products p
JOIN product_ingredients pi ON p.product_id = pi.product_id
GROUP BY p.name
ORDER BY ingredient_count DESC;

-- Query to find the lowest sales day of each week along with the top-selling product on that day
-- This query uses common table expressions (CTEs) to first calculate weekly sales and daily sales.
-- It then identifies the day with the lowest sales in each week and finds the top-selling product on that day.
WITH weekly_sales AS (
    SELECT 
        DATE_TRUNC('week', o.order_date) AS week_start,
        DATE(o.order_date) AS order_day,
        SUM(o.total_price) AS daily_sales
    FROM orders o
    GROUP BY week_start, order_day
),
lowest_sales AS (
    SELECT DISTINCT ON (week_start)
        week_start, order_day, daily_sales
    FROM weekly_sales
    ORDER BY week_start, daily_sales ASC
),
top_seller AS (
    SELECT 
        DATE(o.order_date) AS order_day,
        p.name AS product_name,
        COUNT(i.item_id) AS product_count,
        ROW_NUMBER() OVER (PARTITION BY DATE(o.order_date) ORDER BY COUNT(i.item_id) DESC) AS rank
    FROM orders o
    JOIN items i ON o.order_id = i.item_id
    JOIN products p ON i.item_id = p.product_id
    GROUP BY order_day, p.name
)
SELECT 
    l.week_start,
    l.order_day,
    l.daily_sales AS lowest_sales,
    t.product_name AS top_seller
FROM lowest_sales l
JOIN top_seller t ON l.order_day = t.order_day AND t.rank = 1
ORDER BY l.week_start;