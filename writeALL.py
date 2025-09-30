import csv
import random
import datetime as d

# from faker import Faker #you 'pip install faker' in the project2 directory. you actually probably don't need this
# this file writes entries to a CSV. 
# I think this will be useful to later examine in Excel before copying it to the SQL db



def iterate_days_in_year(year):
    weeks = 39
    start_date = d.date(year, 1, 1)
    current_date = start_date
    while (current_date - start_date).total_seconds() <= weeks * 7 * 24 * 60 * 60:
        yield current_date
        current_date += d.timedelta(days=1)


# goals to meet. the loop to populate 'data' will probably be a while loop based on these 2 conditions being met
revenueGoal = 750000
weekGoal = 39

menu = [
    ("Classic Milk Tea", 6.99), #each item (for now) is a tuple: pair of name + price
    ("Apple Tea", 7.49),
    ("Peach Tea", 7.99),
    ("Lemon Tea", 6.99),
    ("Green Tea", 6.99),
    ("Rasberry Tea", 7.99),
    ("Matcha Tea", 7.99),
    ("Orange Tea", 7.49),
    ("Boba Tea", 7.49),
    ("Camamile Tea", 7.99),
    ("Earl Grey Tea", 7.99),
    ("Taro Tea", 7.99),
    ("Ulong Tea", 7.99),
    ("Peppermint Tea", 7.49),
    ("Black Tea", 7.99),
    ("Rooibos Tea", 7.99),
    ("Apricot Amaretto Tea", 7.49),
    ("Belgian Mint Tea", 7.99),
    ("Caremel Nougat Tea", 7.99),
    ("Cherry Cosmo Tea", 7.99),
    ("Chocolate Fondue Tea", 7.99),
    ("Darjeelinge Quince Tea", 7.49),
    ("Hibiscus Blossom Tea", 7.99),
    ("Honey Hojicha Tea", 7.99)
]
sizes = ['Small', 'Medium', 'Large', 'Bucees Large']
sugar_or_ice = ['0', '50', '75', '100']


data = []
totalRevenue = 0
orderID = 1


days = [day for day in iterate_days_in_year(2024)]

ordersTable     = open('orders.csv', 'w')
itemsTable      = open('items.csv', 'w')
productsTable   = open('products.csv', 'w')

for day in days:
    hour = random.randint(9, 21) # shop open from 9:00 AM - 9:00 PM
    min = random.randint(0, 59)
    time = f"{hour:02d}:{min:02d}" # time    
    
    numItems = random.randint(1,4)
    totalPrice = 0
    orderItemNumber = 1
    for i in range(numItems):
        itemID = str(orderID) + ' ' + orderItemNumber
        item, price = random.choice(menu)
        size = random.choice(sizes)
        sugar = random.choice(sugar_or_ice)
        ice = random.choice(sugar_or_ice)
        milk = random.randint(0,1)
        # Charge for milk added on
        if milk:
            totalPrice += 0.5 
        milk = 'Milk' if milk else 'No Milk'

        totalPrice += price

        item = [
            orderID,
            itemID,
            size,
            sugar,
            ice,
            milk
        ]

    order = [
        orderID,
        day,
        time,
        totalPrice
    ]
    writer = csv.writer(ordersTable)
    writer.writerow(order)
    orderID += 1
    totalRevenue += cost

ordersTable.close()
itemsTable.close()
productsTable.close()



""" POSSIBLE SCHEME

-- Table for orders
CREATE TABLE entries (
    order_id INT PRIMARY KEY,
    order_name VARCHAR(100) NOT NULL
);

-- Table for items (predefined list of possible items)
CREATE TABLE items (
    item_id INT PRIMARY KEY,
    item_name VARCHAR(100) NOT NULL,
    -- Add other item attributes as needed, e.g., price, description
    price DECIMAL(10, 2)
);

-- Junction table to link orders to items
CREATE TABLE order_items (
    order_id INT,
    item_id INT,
    -- Optionally add fields like quantity
    quantity INT DEFAULT 1,
    PRIMARY KEY (order_id, item_id), -- Composite primary key
    FOREIGN KEY (order_id) REFERENCES entries(order_id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES items(item_id) ON DELETE RESTRICT
);

"""