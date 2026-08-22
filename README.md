# ShopFlow Microservices

A production-style e-commerce backend built with Spring Boot and Spring Cloud to learn and demonstrate microservices architecture.

## Services

- User Service
- Product Service
- Inventory Service
- Order Service

## Technologies

- Java
- Spring Boot
- Spring Cloud
- MySQL
- REST
- Kafka
- Docker
- Eureka
- Resilience4j

## Microservices Concepts

This project will cover:

- Service-to-service communication
- Service discovery
- API Gateway
- Load balancing
- Fault tolerance
- Distributed authentication
- Centralized configuration
- Event-driven architecture
- Distributed transactions
- Observability
- Microservices testing

## Services

| Service | Responsibility |
|---|---|
| **User Service** | User registration, authentication, and user management |
| **Product Service** | Product catalog and product information |
| **Inventory Service** | Stock management and availability |
| **Order Service** | Order creation, order processing, and order status |

## API Endpoints

### User Service

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/users` | Register a new user |
| GET | `/api/users/{id}` | Get user by ID |
| GET | `/api/users` | Get all users |

