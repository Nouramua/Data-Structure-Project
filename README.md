# 🛒 E-Commerce Inventory & Order Management System  
### CSC 212 - Section 55024

---

## 🧩 Project Overview  

This project implements an **E-Commerce Inventory & Order Management System** using Java.  
It manages **products, customers, orders, and reviews** for an online store and provides useful analytical queries to help the business make better decisions.

The system allows:
- Adding, removing, and updating products.
- Managing customers and their orders.
- Tracking stock and out-of-stock products.
- Adding and editing product reviews.
- Viewing order history for each customer.
- Extracting reports such as top-rated products, common reviewed products, and all orders between specific dates.

---

## 🎯 Project Objectives  

- Build a **modular and efficient** Java system using Object-Oriented Programming (OOP).  
- Implement **data structures manually** (no Java Collections).  
- Analyze **time and space complexity** for every operation.  
- Provide **functional queries** to simulate real-world e-commerce insights.  

---

## 👥 Team Members  

| Name | Role / Main Tasks |
|------|--------------------|
| **Hessa Alarifi** | Products & Customers classes: Add, update, search, and track stock. |
| **Alanoud Alsanad** | Orders class: Create, cancel, update, and search orders + orders between dates. |
| **Noura Almuayli** | Reviews & Analytical Queries: Add/edit reviews, top 3 products, common reviews, and average ratings. |

---

## 🧱 System Components  

### 1. `Product` Class  
- Attributes: `productId`, `name`, `price`, `stock`, `reviewsList`.  
- Operations:  
  - Add, update, delete product.  
  - Search by ID or name.  
  - Track out-of-stock items.  
  - Manage product reviews.  

### 2. `Customer` Class  
- Attributes: `customerId`, `name`, `email`, `ordersList`.  
- Operations:  
  - Register new customer.  
  - View order history.  

### 3. `Order` Class  
- Attributes: `orderId`, `customerRef`, `productsList`, `totalPrice`, `orderDate`, `status`.  
- Operations:  
  - Create, cancel, or update order.  
  - Search by ID.  
  - Filter orders between two dates.  

### 4. `Review` Class  
- Attributes: `customerId`, `rating`, `comment`.  
- Operations:  
  - Add or edit review.  
  - Calculate average rating per product.  

---

## 🔍 Functional Requirements Implemented  

| Requirement | Description | Implemented By |
|--------------|--------------|----------------|
| Read data from CSV | Import products, customers, orders, and reviews. | Student 1 |
| Add Product/Customer/Order | Create new entries dynamically. | Students 1 & 2 |
| Add/Edit Review | Manage customer feedback for each product. | Student 3 |
| Extract all reviews by a customer | Display all reviews written by a given customer. | Student 3 |
| Suggest Top 3 Products | Based on highest average rating. | Student 3 |
| Orders Between Dates | List all orders within a given date range. | Student 2 |
| Common Reviewed Products | Products reviewed by two customers with rating > 4. | Student 3 |

---

## ⚙️ Implementation Details  

- **Language:** Java  
- **IDE:** IntelliJ IDEA / VS Code  
- **Data Structures Used:** Custom arrays and linked lists (no Java Collections).  
- **File Input:** CSV files for data loading.  
- **Complexity Analysis:**  Each method includes Big-O time and space complexity as code comments.

---
