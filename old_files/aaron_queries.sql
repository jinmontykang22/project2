--Query to find the total number of completed orders in the year 2025
Select count(order_id) as total_orders 
from orders 
where order_status = 'COMPLETE' and order_date >= '2024-12-30';

--Query to find the top 10 customers by total amount spent in completed orders in 2025
Select customer_id, sum(total_amount) as total_spent
from orders
where order_status = 'COMPLETE' and order_date >= '2024-12-30'
group by customer_id
order by total_spent desc
limit 10;

--Query to find the total number of completed orders first week in 2025
Select
    data_truncation('week', order_date) as week_start,
    count(order_id) as total_orders
from
    orders
group by
    week_start
where
    order_status = 'COMPLETE' and week_start = '2024-12-30';

