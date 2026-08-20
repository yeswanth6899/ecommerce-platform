# 🛒 E-Commerce Platform

A production-style backend E-Commerce application built using **Java 21** and **Spring Boot**, following layered architecture, clean code principles, and enterprise backend development practices.

The application implements a complete end-to-end shopping workflow including user authentication, product catalog management, inventory reservation, shopping cart, order placement, payment processing, shipment lifecycle, and notification management.

---

# 🚀 Features

## Authentication & Security

- JWT Authentication & Authorization
- Role-Based Access Control (ADMIN / USER)
- Spring Security
- BCrypt Password Encryption
- Stateless Authentication

---

## User Management

- User Registration
- User Login
- User Profile Management

---

## Category Management

- Create Category
- Update Category
- Delete Category
- View Categories

---

## Product Management

- Create Product
- Update Product
- Delete Product
- View Products
- Category-wise Product Management

---

## Inventory Management

- Inventory Creation
- Inventory Update
- Stock Reservation
- Stock Confirmation
- Stock Release
- Reorder Level Management
- Inventory Reservation Tracking

---

## Shopping Cart

- Add Product to Cart
- Update Cart Quantity
- Remove Cart Item
- Clear Cart
- Cart Total Calculation

---

## Address Management

- Add Shipping Address
- Update Address
- Delete Address
- Default Address Support
- View User Addresses

---

## Order Management

- Place Order
- Order Summary
- Order History
- Order Total Calculation
- Tax Calculation
- Shipping Cost Calculation
- Order Number Generation

---

## Payment Management

- Mock Payment Gateway
- Payment Processing
- Payment Retry Support
- Payment History
- Transaction ID Generation
- Payment Status Tracking

---

## Shipment Management

Shipment lifecycle:

CREATED

↓

PACKED

↓

SHIPPED

↓

OUT_FOR_DELIVERY

↓

DELIVERED

Features

- Shipment Creation
- Tracking Number Generation
- Shipment Status Updates
- Estimated Delivery Date
- Shipping Validation

---

## Notification Management

Notifications generated for:

- Welcome
- Payment Successful
- Payment Failed
- Shipment Created
- Shipment Delivered

---

## Global Features

- Global Exception Handling
- DTO Pattern
- Mapper Pattern
- Request Validation
- Repository-Service-Controller Architecture
- Transaction Management
- Clean Layered Architecture
- PostgreSQL Integration
- RESTful APIs

---

# 🛠 Technology Stack

Backend

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- Maven

Database

- PostgreSQL

Authentication

- JWT
- BCrypt

Development Tools

- IntelliJ IDEA / STS
- Postman
- Git
- GitHub

---

# 📂 Project Structure

```
user-services
│
├── config
├── controller
├── dto
├── entity
├── exception
├── mapper
├── repository
├── response
├── scheduler
├── security
├── service
└── resources
```

---

# 📌 Business Workflow

```
User Registration
        │
        ▼
Login
        │
        ▼
Browse Products
        │
        ▼
Add to Cart
        │
        ▼
Add Shipping Address
        │
        ▼
Place Order
        │
        ▼
Reserve Inventory
        │
        ▼
Process Payment
      /         \
     /           \
 Failed         Paid
    │              │
Release         Confirm
Inventory       Inventory
    │              │
Retry         Create Shipment
                    │
                    ▼
Shipment Lifecycle
CREATED
   │
PACKED
   │
SHIPPED
   │
OUT_FOR_DELIVERY
   │
DELIVERED
                    │
                    ▼
Notifications
```

---

# ✅ Completed Modules

- Authentication
- User Management
- Category Management
- Product Management
- Inventory Management
- Shopping Cart
- Address Management
- Order Management
- Payment Management
- Shipment Management
- Notification Management
- Security
- Global Exception Handling

---

# 🚀 Upcoming Enhancements

- API Gateway
- Microservices Architecture
- Apache Kafka
- Event-Driven Communication
- Docker
- Docker Compose
- Swagger / OpenAPI
- JUnit & Mockito
- Integration Testing
- Resilience4j
- Redis Caching
- Prometheus & Grafana
- CI/CD Pipeline
- AWS Deployment

---

# ▶ Running the Application

Clone the repository

```
git clone https://github.com/yeswanth6899/ecommerce-platform.git
```

Configure environment variables

```
DB_URL
DB_USERNAME
DB_PASSWORD
JWT_SECRET
```

Run the application

```
./mvnw spring-boot:run
```

or

```
mvn spring-boot:run
```

Application URL

```
http://localhost:6868
```

---

# 📖 Current Status

✅ Fully functional monolithic E-Commerce backend.

The application has been thoroughly tested with end-to-end business scenarios, including inventory reservation, payment processing, shipment lifecycle management, and notification generation.

The next phase of development is migrating the application to a **Microservices Architecture** using **Spring Cloud**, **Apache Kafka**, **Docker**, and **API Gateway**.