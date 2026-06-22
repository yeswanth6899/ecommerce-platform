# Database Design

## Overview

The E-Commerce Order Processing Platform uses PostgreSQL as the primary relational database.

Each microservice owns its data and communicates through APIs and events.

Version 1 includes the following entities:

* User
* Product
* Cart Item
* Order
* Order Item
* Payment

---

# Entity Relationship Diagram

```text
User
 |
 | 1:N
 |
Order
 |
 | 1:N
 |
OrderItem
 |
 N:1
 |
Product


User
 |
 | 1:N
 |
CartItem
 |
 N:1
 |
Product


Order
 |
 1:1
 |
Payment
```

---

# Users Table

## Purpose

Stores customer account information.

## Table Name

users

| Column       | Data Type    | Constraints  |
| ------------ | ------------ | ------------ |
| id           | BIGSERIAL    | Primary Key  |
| first_name   | VARCHAR(100) | Not Null     |
| last_name    | VARCHAR(100) | Not Null     |
| email        | VARCHAR(255) | Unique       |
| password     | VARCHAR(255) | Not Null     |
| role         | VARCHAR(50)  | Default USER |
| phone_number | VARCHAR(20)  | Nullable     |
| created_at   | TIMESTAMP    | Not Null     |
| updated_at   | TIMESTAMP    | Not Null     |

---

# Products Table

## Purpose

Stores product catalog information.

## Table Name

products

| Column         | Data Type     | Constraints |
| -------------- | ------------- | ----------- |
| id             | BIGSERIAL     | Primary Key |
| product_name   | VARCHAR(255)  | Not Null    |
| description    | TEXT          | Nullable    |
| category       | VARCHAR(100)  | Not Null    |
| price          | DECIMAL(10,2) | Not Null    |
| stock_quantity | INTEGER       | Not Null    |
| image_url      | VARCHAR(500)  | Nullable    |
| created_at     | TIMESTAMP     | Not Null    |
| updated_at     | TIMESTAMP     | Not Null    |

---

# Cart Items Table

## Purpose

Stores products currently added to user cart.

## Table Name

cart_items

| Column     | Data Type | Constraints |
| ---------- | --------- | ----------- |
| id         | BIGSERIAL | Primary Key |
| user_id    | BIGINT    | Foreign Key |
| product_id | BIGINT    | Foreign Key |
| quantity   | INTEGER   | Not Null    |
| created_at | TIMESTAMP | Not Null    |

---

# Orders Table

## Purpose

Stores order information.

## Table Name

orders

| Column       | Data Type     | Constraints |
| ------------ | ------------- | ----------- |
| id           | BIGSERIAL     | Primary Key |
| user_id      | BIGINT        | Foreign Key |
| total_amount | DECIMAL(10,2) | Not Null    |
| status       | VARCHAR(50)   | Not Null    |
| created_at   | TIMESTAMP     | Not Null    |
| updated_at   | TIMESTAMP     | Not Null    |

## Order Status Values

* PENDING
* PROCESSING
* PAID
* SHIPPED
* DELIVERED
* CANCELLED

---

# Order Items Table

## Purpose

Stores products belonging to an order.

## Table Name

order_items

| Column      | Data Type     | Constraints |
| ----------- | ------------- | ----------- |
| id          | BIGSERIAL     | Primary Key |
| order_id    | BIGINT        | Foreign Key |
| product_id  | BIGINT        | Foreign Key |
| quantity    | INTEGER       | Not Null    |
| unit_price  | DECIMAL(10,2) | Not Null    |
| total_price | DECIMAL(10,2) | Not Null    |

---

# Payments Table

## Purpose

Stores payment transaction information.

## Table Name

payments

| Column          | Data Type     | Constraints |
| --------------- | ------------- | ----------- |
| id              | BIGSERIAL     | Primary Key |
| order_id        | BIGINT        | Foreign Key |
| transaction_id  | VARCHAR(255)  | Unique      |
| payment_method  | VARCHAR(50)   | Not Null    |
| amount          | DECIMAL(10,2) | Not Null    |
| payment_status  | VARCHAR(50)   | Not Null    |
| idempotency_key | VARCHAR(255)  | Unique      |
| created_at      | TIMESTAMP     | Not Null    |

## Payment Status Values

* SUCCESS
* FAILED
* PENDING

---

# Database Indexing Strategy

## Users

```sql
CREATE UNIQUE INDEX idx_users_email
ON users(email);
```

## Products

```sql
CREATE INDEX idx_products_category
ON products(category);
```

## Orders

```sql
CREATE INDEX idx_orders_user
ON orders(user_id);
```

## Payments

```sql
CREATE UNIQUE INDEX idx_payment_transaction
ON payments(transaction_id);
```

---

# Future Enhancements

Version 2

* Inventory Table
* Product Reviews
* Coupons
* Shipping Address
* Wishlist

Version 3

* Elasticsearch
* Audit Tables
* Event Store
