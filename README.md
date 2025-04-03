# SmartInventory - Backend Repository

SmartInventory is a backend system for an advanced inventory management solution, built with **Spring Boot** and **JWT-based authentication**. This system provides RESTful APIs for efficient inventory management, category and supplier management, user role-based access control (**Admin, User**), stock movement tracking, reporting, and user activity logging. It includes automated alerts for **low stock** and **product expiry**, leveraging **PostgreSQL** as the database.

## Features

### 🔹 Authentication
- JWT-based **user registration, login, and logout**.

### 🔹 Category & Supplier Management
- Full **CRUD operations** with user activity logs.

### 🔹 Inventory Management
- **CRUD operations** with consumption tracking.
- **User activity logging** for inventory actions.

### 🔹 Admin Management
- **Only Admin** can create new admin users.

### 🔹 Reports
- **Daily and weekly reports** with CSV file download support.

### 🔹 User Activity Logging
- Tracks all user actions, including login, logout, addition, updates, and deletions.

### 🔹 Alerts & Notifications
- **Low stock** alerts.
- **Product expiry** alerts.

### 🔹 Stock Movement Tracking
- Users can **record and update stock movements**, ensuring transparency in inventory changes.

---

## Tech Stack

- **Language:** Java 17
- **Framework:** Spring Boot 3.4.4
- **Database:** PostgreSQL
- **ORM:** Spring Boot JPA (Hibernate)
- **Security:** Spring Security & JWT
- **Build Tool:** Maven

---

## Installation Guide

### Step 1: Clone the Repository
```bash
  https://github.com/GazalBandil/smartInventory.git
  cd smartInventory
  cd backend
```

### Step 2: Set Up Database & Environment Variables
- Configure your **PostgreSQL database** in `application.properties`.
- Set up **JPA and Security** configurations.
```bash
spring.application.name=backend
spring.datasource.url=jdbc:postgresql://localhost:5432/inventory_db
spring.datasource.username=postgres
spring.datasource.password=000
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database=postgresql
spring.jpa.show-sql=true
spring.security.enabled=false
debug=true  
```
### Step 3: Install Dependencies
```bash
  mvn install
```

### Step 4: Run the Application
```bash
  mvn spring-boot:run
```

### Step 5: Access the API
- The backend will be available at: [http://localhost:8080](http://localhost:8080)
---

## API Endpoints

### 🔹 **Must-Have Functionalities API [Implemented]**

#### **Authentication & User Management**
- `POST /auth/login` → User login (**USER, ADMIN**)
- `POST /auth/create-user` → Register a new user
- `POST /auth/create-admin` → Register an admin (**ADMIN only**)
- `POST /auth/logout` → User logout

#### **Product Management**
- `POST /products/add-item?username={username}` → Add a new product (**ADMIN only**)
- `GET /products` → View all products
- `PUT /products/{id}?username={username}` → Update product details (**ADMIN only**)
- `DELETE /products/{id}?username={username}` → Delete a product (**ADMIN only**)
- `POST /products/consume/{id}/{quantity}` → Consume product quantity

#### **Stock Movement & Alerts**
- `POST /api/stock-movement/record` → Record stock movement
- `GET /api/alerts/low-stock` → Get low stock alerts
- `GET /products/expiry` → Get product expiry alerts

#### **Reports**
- `GET /api/stock-movement/report/daily` → Generate daily stock movement report
- `GET /api/stock-movement/report/weekly` → Generate weekly stock movement report

---

### 🔹 **Good-to-Have Functionalities API [Implemented]**

#### **Product Search & Logging**
- `GET /products/search/{name}` → Search for a product by name
- **User activity logs**: Tracks actions for login, logout, adding, deleting, updating inventory, categories, and suppliers using Params.

#### **Supplier Management**
- `GET /supplier/all-supplier` → View all suppliers
- `POST /supplier/add-supplier` → Add a new supplier
- `PUT /supplier/update/{id}` → Update supplier details
- `DELETE /supplier/delete/{id}` → Delete a supplier

---

## Usage Guide

1. Start the **SmartInventory (backend)** service.
2. Connect it with the **SmatInventory-Frontend**.
3. Use an **Admin account** to manage inventory and create new admins.
4. Regularly check **reports & alerts** to monitor stock and expiry statuses.

---


