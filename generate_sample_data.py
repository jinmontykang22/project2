import csv
import random
from datetime import date, datetime, timedelta
from faker import Faker

"""
Run this and you will see several files in the tables dir.

1) ingredients.csv which holds the tea mixes that would be used to make various teas. This can be edited to add
new items like boba beads that you would then add to the menu item in products by changing one of the flavors to
the new ingredient key.

2) inventory.csv which shows a list of the inventory items that our business are keeping track of and the stock
of units remaining on each item. This can potentially be merged with ingredients, but it didnt make immediate
sense to try so I did not.

3) items.csv will hold the list of every single item ordered by a customer throughout all orders. It has keys
to the product table for the individual product it represents, keys to the order table for the order it is part
of, etc. It also contains a few entries dedicated to size, ice, and more each with a few possible values.

4) products.csv which is the menu basically, each item constructed from various ingredients
but independent from size, and obvious customizable options. The ingredients arent for the customer to see, but
the staff. The price is essentially made up, and will be displayed to the customer along with the name of the 
product.

5) staff.csv created by Aaron, can be extended but I have not gotten to that point yet.

Goodnight pals.
"""
revenueGoal = 750000
weekGoal = 39

# def iterate_days_in_year(year):
#     weekGoal = 39
#     start_date = d.date(year, 1, 1)
#     current_date = start_date
#     while (current_date - start_date).total_seconds() <= weekGoal * 7 * 24 * 60 * 60:
#         yield current_date
#         current_date += d.timedelta(days=1)


"""
This is writeStaff.py, credit: Aaron Liu
"""
fake = Faker()

roles = {
    'Manager': (80000, 90000),
    'Cashier': (28000, 35000),
    'Barista': (27000, 32000),
    'Cook': (30000, 36000), #does this even apply here?
    'Cleaner': (24000, 28000)
}

staff_data = [['staff_id', 'name', 'role', 'salary', 'hours_worked']]

for i in range(1, 11): 
    name = fake.name()
    staff_id = f"S{str(i).zfill(3)}"
    role = random.choice(list(roles.keys())) #might want to add forced amount of certain roles (at least 1 manager, 1 cashier, etc)
    salary = random.randint(*roles[role])
    hours = random.randint(20, 40)
    
    staff_data.append([staff_id, name, role, f"{salary:.2f}", hours])

with open('tables/staff.csv', mode='w', newline='') as file:
    writer = csv.writer(file)
    writer.writerows(staff_data)
"""
End
"""

# products = [
#     # Milk Teas
#     (1, "Milk Tea", "Classic Milk Tea", "", 6.99, True),
#     (2, "Milk Tea", "Taro Tea", "", 6.99, True),
#     (3, "Milk Tea", "Matcha Tea", "", 7.49, True),
#     (4, "Milk Tea", "Chocolate Fondue Tea", "", 7.99, True),
#     (5, "Milk Tea", "Caremel Nougat Tea", "", 7.99, True),
#     (6, "Milk Tea", "CUSTOM TEA", "", 7.99, True),

#     # Fruit Teas
#     (7, "Fruit Tea", "Apple Tea", "", 7.49, True),
#     (8, "Fruit Tea", "Peach Tea", "", 7.99, True),
#     (9, "Fruit Tea", "Lemon Tea", "", 6.99, True),
#     (10, "Fruit Tea", "Green Tea", "", 6.99, True),
#     (11, "Fruit Tea", "Rasberry Tea", "", 7.99, True),
#     (12, "Fruit Tea", "Orange Tea", "", 6.99, True),
#     (13, "Fruit Tea", "Fruity Boba Tea", "", 7.49, True),
#     (14, "Fruit Tea", "Cherry Cosmo Tea", "", 7.99, True),
#     (15, "Fruit Tea", "Darjelinge Quince Tea", "", 7.49, True),
#     (16, "Fruit Tea", "Hibiscus Blossom Tea", "", 7.99, True),
#     (17, "Fruit Tea", "Honey Hojicha Tea", "", 7.99, True),
#     (18, "Fruit Tea", "Belgian Mint Tea", "", 7.99, True),
#     (19, "Fruit Tea", "Apricot Amaretto Tea", "", 7.49, True),

#     # Hot Teas
#     (20, "Hot Tea", "Camamile Tea", "", 6.99, True),
#     (21, "Hot Tea", "Earl Grey Tea", "", 7.99, True),
#     (22, "Hot Tea", "Ulong Tea", "", 7.99, True),
#     (23, "Hot Tea", "Peppermint Tea", "", 7.49, True),
#     (24, "Hot Tea", "Black Tea", "", 7.99, True),
#     (25, "Hot Tea", "Rooibos Tea", "", 7.99, True),
# ]

# """
# Writing Products File
# """
# with open('tables/products.csv', 'w', newline='') as productFile:
#     writer = csv.writer(productFile)
#     writer.writerow(['product_id', 'category', 'name', 'description', 'base_price', 'is_active'])
#     writer.writerows(products)

"""
Writing Ingredients file.
"""

# teas = [
#     (0, "Taro Mix", 300.00, "gram", False),
#     (1, "Black Mix", 500.00, "grams", False),
#     (2, "Apple Mix", 500.00, "grams", False),
#     (3, "Peach Mix", 500.00, "grams", False),
#     (4, "Lemon Mix", 500.00, "grams", False),
#     (5, "Green Mix", 500.00, "grams", False),
#     (6, "Rasberry Mix", 500.00, "grams", False),
#     (7, "Orange Mix", 500.00, "grams", False),
#     (8, "Matcha Mix", 500.00, "grams", False),
#     (9, "Camamile Mix", 500.00, "grams", False),
#     (10, "Peppermint Mix", 500.00, "grams", False),
#     (11, "Honey Mix", 500.00, "grams", False),
#     (12, "Ulong Mix", 500.00, "grams", False),
#     (13, "Rooibos Mix", 500.00, "grams", False),
#     (14, "Caremel Mix", 500.00, "grams", False),
#     (15, "Chocolate Mix", 500.00, "grams", False),
#     (16, "Cherry Mix", 500.00, "grams", False),
#     (17, "Milk", 2000.00, "ml", True),
#     (18, "Pearl Tapioca", 50.00, "packets", False),
#     (19, "Assorted Fruit Jellies", 50.00, "packets", False),
#     (20, "Candy Topping", 50.00, "packets", False),
# ]

# with open('tables/ingredients.csv', 'w', newline='') as ingred:
    
#     writer = csv.writer(ingred)
#     writer.writerow(['ingredient_id', 'name', 'quantity_in_stock', 'unit', 'needs_restocking'])

#     writer.writerows(teas)

# updated products with 'categories' replacing 'temperature'
products = [
    (1, "Classic Milk Tea"      , 6.99, 'milk', '1'    , '21'     , '18'      , '0.50'    , '1', '1'), # 1 for 1 scoop
    (2, "Apple Tea"             , 7.49, 'fruit' , '2'    , '5'      , '0'      , '0.25'    , '0', '2'),
    (3, "Peach Tea"             , 7.99, 'fruit', '3'    , '5'      , '0'      , '0.25'    , '0', '2'),
    (4, "Lemon Tea"             , 6.99, 'fruit', '4'    , '4'      , '0'      , '0.00'    , '0', '2'),
    (5, "Green Tea"             , 6.99, 'hot', '5'    , '5'      , '0'      , '0.00'    , '0', '0'),
    (6, "Rasberry Tea"          , 7.99, 'fruit', '6'    , '0'     , '17'      , '0.00'    , '0', '2'),
    (7, "Matcha Tea"            , 7.49, 'cold', '8'    , '21'     , '18'      , '0.00'    , '1', '0'),
    (8, "Orange Tea"            , 6.99, 'fruit', '7'    , '0'     , '19'      , '0.00'    , '1', '0'),
    (9, "Fruity Boba Tea"       , 7.49, 'fruit', '3'    , '5'      , '19'      , '0.50'    , '1', '0'),
    (10, "Camamile Tea"         , 6.99, 'hot' , '9'    , '0'     , '17'      , '0.25'    , '0', '0'),
    (11, "Earl Grey Tea"        , 7.99, 'hot' , '1'    , '9'      , '0'      , '0.00'    , '0', '0'),
    (12, "Taro Tea"             , 6.99, 'milk' , '22'   , '21'     , '18'      , '0.50'    , '0', '0'),
    (13, "Ulong Tea"            , 7.99, 'hot' , '12'   , '0'     , '17'      , '0.00'    , '0', '0'),
    (14, "Peppermint Tea"       , 7.49, 'hot' , '10'   , '0'      , '20'      , '0.00'    , '2', '1'),
    (15, "Black Tea"            , 7.99, 'hot' , '1'    , '0'     , '17'      , '0.00'    , '0', '0'),
    (16, "Rooibos Tea"          , 7.99, 'hot' , '1'    , '5'      , '0'      , '0.00'    , '1', '1'),
    (17, "Apricot Amaretto Tea" , 7.49, 'fruit' , '15'   , '14'     , '10'      , '0.00'    , '0', '0'),
    (18, "Belgian Mint Tea"     , 7.99, 'hot', '10'   , '6'      , '0'      , '0.00'    , '1', '1'),
    (19, "Caremel Nougat Tea"   , 7.99, 'milk' , '14'   , '1'      , '15'      , '0.25'    , '1', '0'),
    (20, "Cherry Cosmo Tea"     , 7.99, 'fruit', '16'   , '9'      , '14'      , '0.00'    , '0', '1'),
    (21, "Chocolate Fondue Tea" , 7.99, 'milk' , '15'   , '11'     , '21'      , '0.50'    , '1', '0'),
    (22, "Darjelinge Quince Tea", 7.49, 'hot', '8'    , '9'      , '0'      , '0.00'    , '1', '1'),
    (23, "Hibiscus Blossom Tea" , 7.99, 'fruit' , '10'   , '9'      , '0'      , '0.00'    , '1', '1'),
    (24, "Honey Hojicha Tea"    , 7.99, 'fruit', '11'   , '5'      , '12'      , '0.00'    , '1', '1'),
    (25, "CUSTOM TEA"           , 7.99, 'milk', '17'   , '17'     , '17'      , '0.00'    , '0', '0'), 
    # Default values for the custom tea, the staff wll construct this on their own if necessary, but 
    # will be instructed to do so infrequently.
]

"""
Writing Orders table and Items table
"""
# Printing purposes
totalRevenue = 0
peakDays = 0

# holidays list for peakDay logic. expand as needed/wanted
holidays = [
    (1, 1),    # New Year's Day
    (7, 4),    # Independence Day
    (11, 27),  # Thanksgiving (apparently isn't always 27th but whatever)
    (12, 25)   # Christmas!
]

# I don't agree with this day logic. the description states 39 weeks from today, but this assumes 39 weeks in 'x' year, starting from the 1st day of that year.
# i argue that this method of calculating date is more flexible than being restricted to a specific year. 
orders  = []
startDate = datetime.now() - timedelta(weeks=weekGoal) 
endDate = datetime.now()
currentDate = startDate

sizes = ['Small', 'Medium', 'Large', 'Bucees_Large']
sugar_or_ice = ['0', '50', '75', '100']
toppings_options = ['Boba','Jelly','Pudding','None']

items   = []
orderID = 1
itemID = 1

ordersTable     = open('tables/orders.csv', 'w', newline='') #using newline just to be safe. 
itemsTable      = open('tables/items.csv', 'w', newline='')

while (totalRevenue < revenueGoal or currentDate <= endDate):
    maxRange = 150
    minRange = 100
    peakDayThreshold = 170

    peakDay = datetime(2025, 8, 25) # for now, peak day will be 8/25/2025 (first day of school)
        
    numOrders = random.randint(minRange, maxRange)

    if currentDate.date() == peakDay.date():
        numOrders = numOrders * 2 #double the 'numOrders' on this specific day
    if currentDate.weekday() >= 5:
        numOrders = int(numOrders * 1.1) #weekends boost by 10%
    if (currentDate.month, currentDate.day) in holidays:
        numOrders = int(numOrders * 1.2) #holidays boost by 20%

    peakDays += 1 if numOrders >= peakDayThreshold else 0
    for _ in range(numOrders):
        # Generate random time for order_date
        hour = random.randint(9, 21) # shop open from 9:00 AM - 9:00 PM
        minute = random.randint(0, 59)
        second = random.randint(0, 59)
        order_datetime = datetime.combine(currentDate.date(), datetime.min.time()) + timedelta(hours=hour, minutes=minute, seconds=second)

        numItems = random.randint(1, 5)
        totalPrice = 0
        order_items_for_this_order = []

        for _ in range(numItems):
            prd = random.choice(products)
            productID = prd[0]
            base_price = prd[2]

            quantity = random.randint(1,3)
            size = random.choice(sizes)
            sugar_level = random.choice(sugar_or_ice)
            ice_level = random.choice(sugar_or_ice)
            # toppings: random subset from toppings_options excluding 'None' if other toppings chosen
            chosen_toppings = random.sample(toppings_options, random.randint(1, len(toppings_options)))
            if 'None' in chosen_toppings and len(chosen_toppings) > 1:
                chosen_toppings.remove('None')
            toppings_str = ','.join(chosen_toppings)

            # Calculate price modifications
            price = base_price * quantity
            if size == 'Small':
                price -= 0.5 * quantity
            elif size == 'Large':
                price += 0.5 * quantity
            elif size == 'Bucees_Large':
                price += 1.0 * quantity

            # Charge for toppings except 'None'
            if 'Boba' in chosen_toppings:
                price += 0.5 * quantity
            if 'Jelly' in chosen_toppings:
                price += 0.5 * quantity
            if 'Pudding' in chosen_toppings:
                price += 0.5 * quantity

            totalPrice += price

            item = [
                itemID,
                orderID,
                productID,
                quantity,
                size,
                sugar_level,
                ice_level,
                toppings_str,
                round(price,2)
            ]
            order_items_for_this_order.append(item)
            itemID += 1

        tip = round(random.uniform(0,5),2)
        special_notes = fake.sentence() if random.random() < 0.3 else ""

        month = int(order_datetime.isoformat(sep=' ').split(' ')[0].split('-')[1])
        order = [
            orderID,
            order_datetime.isoformat(sep=' '),
            month,
            round(totalPrice,2),
            tip,
            special_notes
        ]
        orderID = orderID+1
        totalRevenue += totalPrice
        orders.append(order)
        items.extend(order_items_for_this_order)

    currentDate += timedelta(days=1)

writer = csv.writer(itemsTable)
writer.writerow(['item_id','order_id','product_id','quantity','size','sugar_level','ice_level','toppings','price'])
writer.writerows(items)

writer = csv.writer(ordersTable)
writer.writerow(['order_id','order_time','month','total_price','tip','special_notes'])
writer.writerows(orders)

ordersTable.close()
itemsTable.close()

print(f'TOTAL REVENUE: {totalRevenue:.02f}\nTOTAL WEEKS: 39\nTOTAL ORDERS: {len(orders)}\nTOTAL ITEMS ORDERED: {len(items)}\nPEAK DAYS: {peakDays}')

# added new ingredients to 1- 16, 18, 21-23 (mostly just adding water to appropriate teas)

"""
Writing Products File
"""
with open('tables/products.csv', 'w', newline='') as productFile:
    writer = csv.writer(productFile)
    writer.writerow(['Product_ID'       , 'Product_Name'    , 'Price'           ,
                     'Category'      , 'Flavor'          , 'Flavor_2'        , 
                     'Flavor_3'         , 'Milk'            , 'Cream'           ,
                     'Sugar'     ])
    
    writer.writerows(products)

"""
Writing ingredients file.
"""

teas = [
    ("Water"         , 1),#0
    ("Black Mix"     , 1),#1
    ("Apple Mix"     , 1),#2
    ("Peach Mix"     , 1),#3
    ("Lemon Mix"     , 1),#4
    ("Green Mix"     , 1),#5
    ("Rasberry Mix"  , 1),#6
    ("Orange Mix"    , 1),#7
    ("Matcha Mix"    , 1),#8
    ("Camamile Mix"  , 1),#9
    ("Peppermint Mix", 1),#10
    ("Honey Mix"     , 1),#11
    ("Ulong Mix"     , 1),#12
    ("Rooibos Mix"   , 1),#13
    ("Caremel Mix"   , 1),#14
    ("Chocolate Mix" , 1),#15
    ("Cherry Mix"    , 1),#16
    ("null", 1),    #17 #added new ingredients 18-22
    ("Pearl Tapioca", 5),#18, one packet of pearls makes 5 servings (teas) 
    ("Assorted Fruit Jellies", 5),#19
    ("Candy Topping", 5),#20
    ("Milk", 4),        #21
    ("Taro Mix", 1)     #22
]

with open('tables/ingredients.csv', 'w', newline='') as ingred:
    
    writer = csv.writer(ingred)
    writer.writerow(['Ing_ID'       , 'Ing_Name', 'NumServings'])

    rows = [(i, name, servings) for i, (name, servings) in enumerate(teas)]
    # for i, tea, num in enumerate(teas):
    #     teas[i] = (i, tea, num)

    writer.writerows(rows)


"""
Writing Inventory file.
"""
with open('tables/inventory.csv', 'w', newline='') as inventoryFile:
    
    writer = csv.writer(inventoryFile)
    writer.writerow(['Inv_Item_ID', 'Name', 'Units_Remaining', 'NumServings'])

    inventory = [ #numServings = how many times an item can be 'used'?
        (1, 'Paper Towel Roll', 30, 100), #numServings indicates how many units (towel/napkin) are on 1 of this item
        (2, 'Napkins', 45, 200), #numServings indicates how many units (towel/napkin) are on 1 of this item
        (3, 'Straws', 40, 1), #numServings indicates how many of this item is required for a drink
        (4, 'Small Cups', 10, 1),
        (5, 'Medium Cups', 3, 1),
        (6, 'Large Cups', 15, 1),
        (7, 'Lids', 10, 1),
        (8, 'Toilet Paper', 21, 100), #numServings indicates how many units (sheets) are on 1 of this item
        (9, 'Soap', 15, 1) #numServings indicates how many how many units (soap bar) are on 1 of this item
    ]

    writer.writerows(inventory)



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