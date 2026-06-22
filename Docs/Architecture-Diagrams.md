# Architecture Diagrams

## High Level Architecture

```text
                    Client
                       |
                       |
                  API Gateway
                       |
------------------------------------------------------
|            |            |           |             |
|            |            |           |             |
v            v            v           v             v

User      Product      Cart       Order       Payment
Service   Service      Service    Service     Service
                                               |
                                               |
                                               v
                                             Kafka
                                               |
                     ---------------------------------------
                     |                                     |
                     v                                     v

             Notification Service               Inventory Service
```

## Service Responsibilities

### User Service
- Registration
- Login
- JWT Authentication
- User Profile Management

### Product Service
- Product Management
- Product Search
- Product Catalog

### Cart Service
- Add Item
- Remove Item
- Update Quantity

### Order Service
- Place Order
- Order Tracking
- Order History

### Payment Service
- Payment Processing
- Retry Mechanism
- Idempotency Handling

### Notification Service
- Email Notifications
- Kafka Event Consumer

### Inventory Service
- Stock Management
- Inventory Updates