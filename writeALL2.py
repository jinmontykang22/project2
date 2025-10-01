import csv
import random
from datetime import date, datetime, timedelta
from faker import Faker

"""
Run this and you will see several files in the tables dir.

1) ingredients.csv which holds the tea mixes that would be used to make various teas. This can be edited to add
new items like boba beads that you would then add to the menu item in products by changing one of the flavors to
the new ingredient key.

2) inventory.csv which shows a list of the inventory items that our business is keeping track of and the stock
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

staff_data = [['Name', 'ID', 'Role', 'Salary', 'Hours']]

for i in range(1, 11): 
    name = fake.name()
    staff_id = f"S{str(i).zfill(3)}"
    role = random.choice(list(roles.keys())) #might want to add forced amount of certain roles (at least 1 manager, 1 cashier, etc)
    salary = random.randint(*roles[role])
    hours = random.randint(20, 40)
    
    staff_data.append([name, staff_id, role, salary, hours])

with open('tables/staff.csv', mode='w', newline='') as file:
    writer = csv.writer(file)
    writer.writerows(staff_data)
"""
End
"""

products = [
    (1, "Classic Milk Tea"      , 6.99, 'cold', '1'    , '21'     , '18'      , '0.50'    , '1', '1'), # 1 for 1 scoop
    (2, "Apple Tea"             , 7.49, 'hot' , '2'    , '5'      , '0'      , '0.25'    , '0', '2'),
    (3, "Peach Tea"             , 7.99, 'cold', '3'    , '5'      , '0'      , '0.25'    , '0', '2'),
    (4, "Lemon Tea"             , 6.99, 'cold', '4'    , '4'      , '0'      , '0.00'    , '0', '2'),
    (5, "Green Tea"             , 6.99, 'cold', '5'    , '5'      , '0'      , '0.00'    , '0', '0'),
    (6, "Rasberry Tea"          , 7.99, 'cold', '6'    , '0'     , '17'      , '0.00'    , '0', '2'),
    (7, "Matcha Tea"            , 7.49, 'cold', '8'    , '21'     , '18'      , '0.00'    , '1', '0'),
    (8, "Orange Tea"            , 6.99, 'cold', '7'    , '0'     , '19'      , '0.00'    , '1', '0'),
    (9, "Fruity Boba Tea"       , 7.49, 'cold', '3'    , '5'      , '19'      , '0.50'    , '1', '0'),
    (10, "Camamile Tea"         , 6.99, 'hot' , '9'    , '0'     , '17'      , '0.25'    , '0', '0'),
    (11, "Earl Grey Tea"        , 7.99, 'hot' , '1'    , '9'      , '0'      , '0.00'    , '0', '0'),
    (12, "Taro Tea"             , 6.99, 'hot' , '22'   , '21'     , '18'      , '0.50'    , '0', '0'),
    (13, "Ulong Tea"            , 7.99, 'hot' , '12'   , '0'     , '17'      , '0.00'    , '0', '0'),
    (14, "Peppermint Tea"       , 7.49, 'hot' , '10'   , '0'      , '20'      , '0.00'    , '2', '1'),
    (15, "Black Tea"            , 7.99, 'hot' , '1'    , '0'     , '17'      , '0.00'    , '0', '0'),
    (16, "Rooibos Tea"          , 7.99, 'hot' , '1'    , '5'      , '0'      , '0.00'    , '1', '1'),
    (17, "Apricot Amaretto Tea" , 7.49, 'hot' , '15'   , '14'     , '10'      , '0.00'    , '0', '0'),
    (18, "Belgian Mint Tea"     , 7.99, 'cold', '10'   , '6'      , '0'      , '0.00'    , '1', '1'),
    (19, "Caremel Nougat Tea"   , 7.99, 'hot' , '14'   , '1'      , '15'      , '0.25'    , '1', '0'),
    (20, "Cherry Cosmo Tea"     , 7.99, 'cold', '16'   , '9'      , '14'      , '0.00'    , '0', '1'),
    (21, "Chocolate Fondue Tea" , 7.99, 'hot' , '15'   , '11'     , '21'      , '0.50'    , '1', '0'),
    (22, "Darjelinge Quince Tea", 7.49, 'cold', '8'    , '9'      , '0'      , '0.00'    , '1', '1'),
    (23, "Hibiscus Blossom Tea" , 7.99, 'hot' , '10'   , '9'      , '0'      , '0.00'    , '1', '1'),
    (24, "Honey Hojicha Tea"    , 7.99, 'cold', '11'   , '5'      , '12'      , '0.00'    , '1', '1'),
    (25, "CUSTOM TEA"           , 7.99, 'cold', '17'   , '17'     , '17'      , '0.00'    , '0', '0'), 
    # Default values for the custom tea, the staff wll construct this on their own if necessary, but 
    # will be instructed to do so infrequently.
]

# added new ingredients to 1- 16, 18, 21-23 (mostly just adding water to appropriate teas)

"""
Writing Products File
"""
with open('tables/products.csv', 'w', newline='') as productFile:
    writer = csv.writer(productFile)
    writer.writerow(['Product_ID'       , 'Product_Name'    , 'Price'           ,
                     'Temperature'      , 'Flavor'          , 'Flavor_2'        , 
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

# days = [day for day in iterate_days_in_year(2024)] 
sizes = ['Small', 'Medium', 'Large', 'Bucees_Large']
sugar_or_ice = ['0', '50', '75', '100']

items   = []
orderID = 1

ordersTable     = open('tables/orders.csv', 'w', newline='') #using newline just to be safe. 
itemsTable      = open('tables/items.csv', 'w', newline='')

# i think that the loop considering both conditions is more natural. it guarantees both conditions are met. 
# previously, it considered only the number of weeks, and math was probably done to ensure the revenue was 
# met by price/numItems/numOrders. maybe either way is fine. 
while (totalRevenue < revenueGoal or currentDate <= endDate):
    # CHANGE THESE NUMBERS TO INCREASE OR DECREASE THE TOTAL REVENUE, TOTAL ORDERS, etc.
    maxRange = 150
    minRange = 100
    peakDayThreshold = 170
    # factor = 1

    # SET UP PEAK DAY LOGIC
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
        hour = random.randint(9, 21) # shop open from 9:00 AM - 9:00 PM
        min = random.randint(0, 59)
        time = f"{hour:02d}:{min:02d}" # time 
        numItems = random.randint(1, 5)
        totalPrice = 0
        orderItemNumber = 1 
        # This will be an extension to the orderID, like rooms are to floors,
        # the first item of the first order will have itemID = 11, second ID = 12
        for _ in range(numItems):
            prd = random.choice(products)
            productID = prd[0]
            itemID = str(orderID) + '.' + str(orderItemNumber) # is itemID going to be a primary key? it may be difficult to read/search in current format (maybe '_' or '.' instead of space)
            size = random.choice(sizes) #should size affect price? what does the price in products represent (the standard drink)? is medium the assumption?
            if (size == 'Small'):
                totalPrice -= 0.5
            elif (size == 'Large'):
                totalPrice += 0.5
            elif (size == 'Bucees_Large'):
                totalPrice += 1.0
            sugar = random.choice(sugar_or_ice)
            ice = random.choice(sugar_or_ice)
            extra_milk = random.randint(0,1)
            # Charge for milk added on
            if extra_milk:
                totalPrice += 0.5 
            # milk = 'Milk' if extra_milk else 'No Milk' #doesn't seem like it's ('milk') used

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
            orderItemNumber += 1 #i assume this was missing
            items.append(item)

        order = [
            orderID,
            currentDate.date().isoformat(),
            time,
            totalPrice,
        ]
        orderID += 1
        totalRevenue += totalPrice
        orders.append(order)

    currentDate += timedelta(days=1)

writer = csv.writer(itemsTable)
writer.writerow(['Order_ID', 'Product_ID', 'Item_ID', 'Size', 'Sugar', 'Ice', 'Extra_Milk'])
writer.writerows(items)

writer = csv.writer(ordersTable)
writer.writerow(['Order_ID', 'Day', 'Time', 'Price'])
writer.writerows(orders)

ordersTable.close()
itemsTable.close()

print(f'TOTAL REVENUE: {totalRevenue:.02f}\nTOTAL WEEKS: 39\nTOTAL ORDERS: {len(orders)}\nTOTAL ITEMS ORDERED: {len(items)}\nPEAK DAYS: {peakDays}')


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