# Coupon API

REST API for creating, managing and applying discount coupons.

The project was built with Java and Spring Boot, focusing on business rules, security, automated testing and concurrency control.

## Features

- Create, update, list and delete coupons
- Search coupons by code
- Pagination and filtering
- Apply discount coupons to purchases
- Coupon expiration and minimum purchase validation
- Usage limit control
- Activate and deactivate coupons
- JWT authentication
- Global exception handling
- Concurrency control with pessimistic locking
- Automated tests with JUnit 5
- Swagger/OpenAPI documentation
- Docker and Docker Compose
- Continuous Integration with GitHub Actions

## Business Rules

When a coupon is applied, the API validates:

1. The coupon exists
2. The coupon is active
3. The coupon has not expired
4. The purchase meets the minimum amount
5. The usage limit has not been reached

After a successful application, the coupon usage count is incremented.

The application uses `BigDecimal` for discount calculations and `PESSIMISTIC_WRITE` locking to prevent concurrent requests from exceeding the coupon usage limit.

## Technologies

- Java
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA / Hibernate
- PostgreSQL
- JUnit 5
- Swagger / OpenAPI
- Maven
- Docker
- Docker Compose
- GitHub Actions

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/coupons` | Create a coupon |
| `GET` | `/coupons` | List coupons |
| `GET` | `/coupons/{code}` | Get a coupon by code |
| `PUT` | `/coupons/{code}` | Update a coupon |
| `DELETE` | `/coupons/{code}` | Delete a coupon |
| `POST` | `/coupons/apply` | Apply a coupon |

The coupon listing supports pagination and filters.

## Authentication

Protected endpoints require a JWT token in the request header:

```text
Authorization: Bearer <token>
```

## Example

Apply a coupon:

```http
POST /coupons/apply
Authorization: Bearer <token>
Content-Type: application/json
```

```json
{
  "code": "DESCONTO20",
  "amount": 100.00
}
```

Example response:

```json
{
  "code": "DESCONTO20",
  "originalAmount": 100.00,
  "finalAmount": 80.00,
  "discount": 20.00
}
```

## Running the Project

Create a `.env` file in the project root:

```env
JWT_SECRET=your-secret-key
DB_URL=jdbc:postgresql://localhost:5432/coupon
DB_USERNAME=postgres
DB_PASSWORD=your-password
```

Run the application and PostgreSQL with Docker:

```bash
docker compose up --build
```

Or run the application directly with Maven:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

## Tests

Run the automated tests with:

```bash
./mvnw test
```

The test suite covers business rules, validation and concurrent coupon usage.

## Continuous Integration

GitHub Actions automatically runs the test suite on pushes to `main` and pull requests targeting `main`.

The CI environment sets up Java 17, starts a temporary PostgreSQL database and runs the automated tests.

Workflow:

```text
.github/workflows/ci.yml
```

## API Documentation

The API is documented using Swagger/OpenAPI.

With the application running, access:

```text
http://localhost:8080/swagger-ui.html
```

## Project Structure

```text
src/main/java/com/arthur/coupon_api
├── config
├── controller
├── dto
├── entity
├── exception
├── repository
└── service
```
