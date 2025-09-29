import csv

staff_data = [
    ['Name', 'ID', 'Role', 'Salary', 'Hours'],
    ['Alice Johnson', 'S001', 'Manager', 85000, 40],
    ['Bob Smith', 'S002', 'Cashier', 30000, 35],
    ['Cindy Lee', 'S003', 'Barista', 28000, 30],
    ['Daniel Kim', 'S004', 'Cook', 32000, 38],
    ['Emma Davis', 'S005', 'Cleaner', 25000, 20]
]

with open('staff.csv', mode='w', newline='') as file:
    writer = csv.writer(file)
    writer.writerows(staff_data)

print("staff.csv created successfully.")