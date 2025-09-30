import csv
import random
from datetime import date, datetime, timedelta

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

# these vars are to determine the date 'weekGoal' weeks before today, using timedelta
# the csv will be populated in chronological order, day by day. 
startDate = datetime.now() - timedelta(weeks=weekGoal) 
endDate = datetime.now()



currentDate = startDate #chronologically, start from the earliest date
while (totalCost < revenueGoal or currentDate <= endDate): #the loop will run until revenueGoal is met AND the weekGoal is met
    numOrders = random.randint(150, 250) #for each day, there will be 'x' amount of orders 

    # SET UP PEAK DAY LOGIC
    peakDay = datetime(2025, 8, 25) # for now, peak day will be 8/25/2025 (first day of school)
    
    if currentDate.date() == peakDay.date():
        numOrders = numOrders * 2 #double the 'numOrders' on this specific day

    # loop creates 'numOrders' orders
    for i in range(numOrders):
        hour = random.randint(9, 21) # shop open from 9:00 AM - 9:00 PM
        min = random.randint(0, 59)
        time = f"{hour:02d}:{min:02d}" # time
        
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
            currentDate.date().isoformat(),
            time,
            cost,
            item
        ]
        data.append(order)
        orderID += 1
        totalCost += cost

    currentDate += timedelta(days=1)

with open('orders.csv', 'w', newline='') as csvfile:
    writer = csv.writer(csvfile)
    writer.writerow(['OrderID', 'Status', 'Date', 'Time', 'Cost', 'Item'])
    writer.writerows(data)


