import csv
import random
import datetime as d

"""
* This will create several files. products.csv which is the menu, each item constructed from various ingredients
but independent from size, and obvious customizable options. The ingredients arent for the customer to see, but
the staff. The price is essentially made up, and will be displayed to the customer along with the name of the 
product.
"""
revenueGoal = 750000
weekGoal = 39

def iterate_days_in_year(year):
    weekGoal = 39
    start_date = d.date(year, 1, 1)
    current_date = start_date
    while (current_date - start_date).total_seconds() <= weekGoal * 7 * 24 * 60 * 60:
        yield current_date
        current_date += d.timedelta(days=1)




products = [
    (1, "Classic Milk Tea"      , 6.99, 'cold', '1'    , '17'     , '17'      , '0.50'    , '1', '1'), # 1 for 1 scoop
    (2, "Apple Tea"             , 7.49, 'hot' , '2'    , '5'      , '17'      , '0.25'    , '0', '2'),
    (3, "Peach Tea"             , 7.99, 'cold', '3'    , '5'      , '17'      , '0.25'    , '0', '2'),
    (4, "Lemon Tea"             , 6.99, 'cold', '4'    , '4'      , '17'      , '0.00'    , '0', '2'),
    (5, "Green Tea"             , 6.99, 'cold', '5'    , '5'      , '17'      , '0.00'    , '0', '0'),
    (6, "Rasberry Tea"          , 7.99, 'cold', '6'    , '17'     , '17'      , '0.00'    , '0', '2'),
    (7, "Matcha Tea"            , 7.49, 'cold', '8'    , '17'     , '17'      , '0.00'    , '1', '0'),
    (8, "Orange Tea"            , 6.99, 'cold', '7'    , '17'     , '17'      , '0.00'    , '1', '0'),
    (9, "Boba Tea"              , 7.49, 'cold', '3'    , '5'      , '17'      , '0.50'    , '1', '0'),
    (10, "Camamile Tea"         , 6.99, 'hot' , '9'    , '17'     , '17'      , '0.25'    , '0', '0'),
    (11, "Earl Grey Tea"        , 7.99, 'hot' , '1'    , '9'      , '17'      , '0.00'    , '0', '0'),
    (12, "Taro Tea"             , 6.99, 'hot' , '10'   , '11'     , '17'      , '0.50'    , '0', '0'),
    (13, "Ulong Tea"            , 7.99, 'hot' , '12'   , '17'     , '17'      , '0.00'    , '0', '0'),
    (14, "Peppermint Tea"       , 7.49, 'hot' , '10'   , '1'      , '17'      , '0.00'    , '2', '1'),
    (15, "Black Tea"            , 7.99, 'hot' , '1'    , '17'     , '17'      , '0.00'    , '0', '0'),
    (16, "Rooibos Tea"          , 7.99, 'hot' , '1'    , '5'      , '10'      , '0.00'    , '1', '1'),
    (17, "Apricot Amaretto Tea" , 7.49, 'hot' , '15'   , '14'     , '10'      , '0.00'    , '0', '0'),
    (18, "Belgian Mint Tea"     , 7.99, 'cold', '10'   , '6'      , '17'      , '0.00'    , '1', '1'),
    (19, "Caremel Nougat Tea"   , 7.99, 'hot' , '14'   , '1'      , '15'      , '0.25'    , '1', '0'),
    (20, "Cherry Cosmo Tea"     , 7.99, 'cold', '16'   , '9'      , '14'      , '0.00'    , '0', '1'),
    (21, "Chocolate Fondue Tea" , 7.99, 'hot' , '15'   , '11'     , '17'      , '0.50'    , '1', '0'),
    (22, "Darjelinge Quince Tea", 7.49, 'cold', '8'    , '9'      , '17'      , '0.00'    , '1', '1'),
    (23, "Hibiscus Blossom Tea" , 7.99, 'hot' , '10'   , '9'      , '17'      , '0.00'    , '1', '1'),
    (24, "Honey Hojicha Tea"    , 7.99, 'cold', '11'   , '5'      , '12'      , '0.00'    , '1', '1'),
    (25, "CUSTOM TEA"           , 7.99, 'cold', '17'   , '17'     , '17'      , '0.00'    , '0', '0'), 
    # Default values for the custom tea, the staff wll construct this on their own if necessary, but 
    # will be instructed to do so infrequently.
]

"""
Writing Products File
"""
with open('tables/products.csv', 'w') as productFile:
    writer = csv.writer(productFile)
    writer.writerow(['Product ID'       , 'Product Name'    , 'Price'           ,
                     'Temperature'      , 'Flavor'          , 'Flavor 2'        , 
                     'Flavor 3'         , 'Milk'            , 'Cream'           ,
                     'Sugar'     ])
    
    writer.writerows(products)

"""
Writing ingredients file.
"""
with open('tables/ingredients.csv', 'w') as ingred:
    
    writer = csv.writer(ingred)
    writer.writerow(['Tea Mix ID'       , 'Tea Name'])

    teas = [
        "Water"         ,
        "Black Mix"     ,
        "Apple Mix"     ,
        "Peach Mix"     ,
        "Lemon Mix"     ,
        "Green Mix"     ,
        "Rasberry Mix"  ,
        "Orange Mix"    ,
        "Matcha Mix"    ,
        "Camamile Mix"  ,
        "Peppermint Mix",
        "Honey Mix"     ,
        "Ulong Mix"     ,
        "Rooibos Mix"   ,
        "Caremel Mix"   ,
        "Chocolate Mix" ,
        "Cherry Mix"    ,
        "null"
    ]

    for i, tea in enumerate(teas):
        teas[i] = (i, tea)

    writer.writerows(teas)


"""
Writing Orders table and Items table
"""
days = [day for day in iterate_days_in_year(2024)]
sizes = ['Small', 'Medium', 'Large', 'Bucees Large']
sugar_or_ice = ['0', '50', '75', '100']
totalRevenue = 0
orderID = 1

ordersTable     = open('tables/orders.csv', 'w')
itemsTable      = open('tables/items.csv', 'w')
orders  = []
items   = []
for day in days:
    hour = random.randint(9, 21) # shop open from 9:00 AM - 9:00 PM
    min = random.randint(0, 59)
    time = f"{hour:02d}:{min:02d}" # time    
    
    numItems = random.randint(1,4)
    totalPrice = 0
    orderItemNumber = 1 
    # This will be an extension to the orderID, like rooms are to floors,
    # the first item of the first order will have itemID = 11, second ID = 12
    for i in range(numItems):
        prd = random.choice(products)
        productID = prd[0]
        itemID = str(orderID) + ' ' + str(orderItemNumber)
        size = random.choice(sizes)
        sugar = random.choice(sugar_or_ice)
        ice = random.choice(sugar_or_ice)
        extra_milk = random.randint(0,1)
        # Charge for milk added on
        if extra_milk:
            totalPrice += 0.5 
        milk = 'Milk' if extra_milk else 'No Milk'

        item = [
            orderID,
            productID,
            itemID,
            size,
            sugar,
            ice,
            extra_milk
        ]

        totalPrice += prd[2]
        items.append(item)

    order = [
        orderID,
        day,
        time,
        totalPrice
    ]
    orderID += 1
    totalRevenue += totalPrice
    orders.append(order)

writer = csv.writer(itemsTable)
writer.writerow(['Order ID', 'Product ID', 'Item ID', 'Size', 'Sugar', 'Ice', 'Extra Milk'])
writer.writerows(items)

writer = csv.writer(ordersTable)
writer.writerow(['Order ID', 'Day', 'Time', 'Total Price'])
writer.writerows(orders)

ordersTable.close()
itemsTable.close()



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

-- 

"""