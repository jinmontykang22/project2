import csv
from faker import Faker
import random

fake = Faker()

roles = {
    'Manager': (80000, 90000),
    'Cashier': (28000, 35000),
    'Barista': (27000, 32000),
    'Cook': (30000, 36000),
    'Cleaner': (24000, 28000)
}

staff_data = [['Name', 'ID', 'Role', 'Salary', 'Hours']]

for i in range(1, 11): 
    name = fake.name()
    staff_id = f"S{str(i).zfill(3)}"
    role = random.choice(list(roles.keys()))
    salary = random.randint(*roles[role])
    hours = random.randint(20, 40)
    
    staff_data.append([name, staff_id, role, salary, hours])

with open('staff.csv', mode='w', newline='') as file:
    writer = csv.writer(file)
    writer.writerows(staff_data)

print("staff.csv created successfully using Faker.")