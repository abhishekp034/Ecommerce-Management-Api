# Product & Customer Management System

Java fullstack CRUD application built with Spring Boot, REST APIs, Thymeleaf, JDBC, and H2.

## Highlights

- Manages 220 seeded product records plus customer and order data.
- Full CRUD across 4 modules: dashboard, products, customers, and customer orders.
- Modular 3-layer OOP structure: controller, service, repository.
- JDBC persistence with optimized SQL, joins, aggregate reports, and indexed lookup columns.
- Browser UI with responsive views for tracking products and customer relationships.
- JSON REST APIs for products, customers, orders, dashboard stats, and low-stock alerts.

## Run

```bash
mvn spring-boot:run
```

Open `http://localhost:8080`.

H2 console: `http://localhost:8080/h2-console`

- JDBC URL: `jdbc:h2:mem:pcms`
- User: `sa`
- Password: blank

## REST API

Base URL: `http://localhost:8080/api`

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/products?keyword=` | List or search products |
| GET | `/products/{id}` | Get one product |
| POST | `/products` | Create product |
| PUT | `/products/{id}` | Update product |
| DELETE | `/products/{id}` | Delete product |
| GET | `/customers?keyword=` | List or search customers |
| GET | `/customers/{id}` | Get one customer |
| POST | `/customers` | Create customer |
| PUT | `/customers/{id}` | Update customer |
| DELETE | `/customers/{id}` | Delete customer |
| GET | `/orders` | List orders |
| GET | `/orders/{id}` | Get one order |
| POST | `/orders` | Create order |
| PUT | `/orders/{id}` | Update order |
| DELETE | `/orders/{id}` | Delete order |
| GET | `/dashboard/stats` | Dashboard totals |
| GET | `/dashboard/low-stock` | Low-stock products |

Example product payload:

```json
{
  "sku": "SKU-API-001",
  "name": "API Test Monitor",
  "category": "Displays",
  "price": 12999.00,
  "quantity": 8,
  "status": "ACTIVE"
}
```

## Test

```bash
mvn test
```
