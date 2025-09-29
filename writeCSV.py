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
    ("Classic Milk Tea", 7.00), #each item (for now) is a tuple: pair of name + price
    ("Fruit Tea", 7.50),
    ("Taro Tea", 8.00),
    ("Matcha Tea", 8.00)
]

# this loop populates 'data', a list of lists (each list is an order)
data = []
totalCost = 0
orderID = 1
days = [day for day in iterate_days_in_year(2024)]

for day in days:
    hour = random.randint(9, 21) # shop open from 9:00 AM - 9:00 PM
    min = random.randint(0, 59)
    time = f"{hour:02d}:{min:02d}" # time

    #need to generate random date 39 weeks from now
    
    
    item, cost = random.choice(menu) #picks random item from 'menu', item
    numItems = random.randint(1,3)

    if numItems != 1: # randomly generates multiple items per order
        for i in range(numItems - 1):
            item2, cost2 = random.choice(menu)
            item = item + ", " + item2
            cost += cost2

    order = [
        orderID,
        "Complete", #do we really need status as an Attribute? in the case of seeding the db, we wouldn't input a "Incomplete/Cancelled" order
        day,
        time,
        cost,
        item,
        numItems
    ]
    data.append(order)
    orderID += 1
    totalCost += cost

with open('testCSV2.csv', 'w', newline='') as csvfile:
    writer = csv.writer(csvfile)
    writer.writerow(['OrderID', 'Status', 'Date', 'Time', 'Cost', 'Item', 'NumItems(TEST)'])
    writer.writerows(data)


