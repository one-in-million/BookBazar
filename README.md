# BookBazar - Online Bookstore API

BookBazar is a robust Spring Boot RESTful API designed for managing an online bookstore. This project demonstrates a clean layered architecture, automated business logic for taxes and discounts, and efficient inventory management.

---

## 🚀 Features

- **Book Management**: Browse books with details including Category, Author, Price, and Live Stock levels.
- **Advanced Search**: Search for books by Title, Author, or Category using a single keyword.
- **Budget Filter**: Filter books based on a maximum price threshold.
- **Smart Ordering System**:
    - **GST Calculation**: Automatically applies a **5% GST** to all orders.
    - **Discount Logic**: Automatically applies a **10% Discount** if the total amount (after GST) exceeds **₹1000**.
- **Inventory Control**: 
    - Validates stock availability before placing an order.
    - Automatically restores stock if an order is cancelled.
- **Order History**: Paginated history for users to view their past purchases.

---

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA** (MySQL)
- **Lombok** (for clean code)
- **Maven** (Dependency Management)

---

## 📋 Prerequisites

- **JDK 17** or higher
- **MySQL Server**
- **Maven** (Optional, Maven Wrapper is included)

---

## ⚙️ Local Setup Instructions

### 1. Clone the Repository
```bash
git clone [https://github.com/one-in-million/BookBazar.git](https://github.com/one-in-million/BookBazar.git)
cd BookBazar
```
### 2. Database Configuration

1. Open **MySQL Workbench** and create a database named `bookstore_db`:
   ```sql
   CREATE DATABASE bookstore_db;
   ```
2. Open `src/main/resources/application.properties` and update your MySQL credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/bookstore_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
### 3. Run the Application
Using the Maven Wrapper included in the project:

```bash
# Windows
./mvnw spring-boot:run

# Mac/Linux
./mvnw spring-boot:run

```
### 4. Automatic Data Initialization
The application is configured to automatically seed the database with **3 Users** and **10 Books** (including authors and categories) upon startup so you can begin testing immediately.


## 🧪 API Testing Guide (Postman)

### 1. Browse & Search Books
| Action | Method | URL |
| :--- | :--- | :--- |
| **View All Books** | `GET` | `http://localhost:8080/books/search` |
| **Search (Keyword)** | `GET` | `http://localhost:8080/books/search?keyword=Java` |
| **Filter by Price** | `GET` | `http://localhost:8080/books/search?maxPrice=1000` |

### 2. Ordering System (Logic Verification)
**Scenario: Place an Order (Triggering 10% Discount)**
- **Endpoint**: `POST http://localhost:8080/orders`
- **Body**:
```json
{
    "userId": 1,
    "items": [
        {
            "bookId": 1,
            "quantity": 1
        }
    ]
}
```
*Logic: If Book 1 costs ₹1200, the system calculates: (₹1200 + 5% GST = ₹1260). Since ₹1260 > ₹1000, a 10% discount is applied. **Final Total: ₹1134.0**.*

### 3. Order History & Management
| Action | Method | URL |
| :--- | :--- | :--- |
| **View User History** | `GET` | `http://localhost:8080/orders/user/1?page=0&size=5` |
| **Cancel Order** | `PUT` | `http://localhost:8080/orders/{orderId}/cancel` |

---

## 📐 Architecture Overview

The project follows a **Layered Architecture** to ensure separation of concerns and maintainability:

- **Controller Layer**: Handles incoming REST requests and maps them to service methods.
- **Service Layer**: Contains all business logic, including GST calculation, discount application, and stock validation.
- **Repository Layer**: Interfaces with the MySQL database using Spring Data JPA.
- **Entity Layer**: Defines the data models (User, Book, Order, OrderItem) and their relationships.



---

## 🔗 Project Link
**GitHub Repository:** [https://github.com/one-in-million/BookBazar.git](https://github.com/one-in-million/BookBazar.git)

---
**Developed as part of a Technical Assessment.**
  
   
