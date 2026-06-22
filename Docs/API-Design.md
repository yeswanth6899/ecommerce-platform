# API Design

## Overview

This document defines the REST APIs exposed by each microservice in the E-Commerce Order Processing Platform.

Base URL:

```http
http://localhost:8080/api
```

---

# User Service

## Register User

### Endpoint

```http
POST /users/register
```

### Request

```json
{
  "firstName": "Yeswanth",
  "lastName": "Kumar",
  "email": "yeswanth@gmail.com",
  "password": "Password123"
}
```

### Response

```json
{
  "id": 1,
  "message": "User registered successfully"
}
```

---

## Login User

### Endpoint

```http
POST /users/login
```

### Request

```json
{
  "email": "yeswanth@gmail.com",
  "password": "Password123"
}
```

### Response

```json
{
  "token": "jwt-token"
}
```

---

## Get User

### Endpoint

```http
GET /users/{id}
```

---

# Product Service

## Create Product

### Endpoint

```http
POST /products
```

### Request

```json
{
  "productName": "iPhone 16",
  "description": "Apple Smartphone",
  "category": "Mobile",
  "price": 999.99,
  "stockQuantity": 100
}
```

---

## Get Product

### Endpoint

```http
GET /products/{id}
```

---

## Get All Products

### Endpoint

```http
GET /products
```

### Query Parameters

```http
?page=0
&size=10
&sort=price
```

---

## Update Product

### Endpoint

```http
PUT /products/{id}
```

---

## Delete Product

### Endpoint

```http
DELETE /products/{id}
```

---

# Cart Service

## Add Product To Cart

### Endpoint

```http
POST /cart
```

### Request

```json
{
  "userId": 1,
  "productId": 100,
  "quantity": 2
}
```

---

## View Cart

### Endpoint

```http
GET /cart/{userId}
```

---

## Remove Cart Item

### Endpoint

```http
DELETE /cart/{cartItemId}
```

---

# Order Service

## Place Order

### Endpoint

```http
POST /orders
```

### Request

```json
{
  "userId": 1,
  "items": [
    {
      "productId": 100,
      "quantity": 2
    }
  ]
}
```

### Response

```json
{
  "orderId": 1001,
  "status": "PENDING"
}
```

---

## Get Order

### Endpoint

```http
GET /orders/{id}
```

---

## Cancel Order

### Endpoint

```http
DELETE /orders/{id}
```

---

# Payment Service

## Process Payment

### Endpoint

```http
POST /payments
```

### Request

```json
{
  "orderId": 1001,
  "paymentMethod": "CARD",
  "amount": 1999.98
}
```

### Response

```json
{
  "transactionId": "TXN123456",
  "status": "SUCCESS"
}
```

---

## Get Payment Details

### Endpoint

```http
GET /payments/{id}
```

---

# Notification Service

## Send Notification

### Endpoint

```http
POST /notifications
```

### Request

```json
{
  "email": "user@gmail.com",
  "message": "Order Created Successfully"
}
```

---

# HTTP Status Codes

| Status Code | Description           |
| ----------- | --------------------- |
| 200         | Success               |
| 201         | Created               |
| 400         | Bad Request           |
| 401         | Unauthorized          |
| 403         | Forbidden             |
| 404         | Not Found             |
| 409         | Conflict              |
| 500         | Internal Server Error |

---

# Future APIs

Version 2

* Product Reviews
* Wishlist
* Coupons
* Inventory Management
* Shipping Address APIs

Version 3

* Search APIs using Elasticsearch
* Recommendation APIs
* Analytics APIs
