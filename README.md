# Promotion Engine API

A simple Spring Boot API for calculating checkout totals with promotional rules.

## Overview

This application provides a REST API for calculating checkout totals with various promotional rules including bulk discounts and combo offers. The codebase is designed to be straightforward and maintainable.

## Features

- **Bulk Promotions**: Buy X quantity of a SKU for a special price
- **Combo Promotions**: Buy two specific SKUs together for a special price
- **REST API**: Simple endpoints for checkout calculation and SKU information
- **Input Validation**: Comprehensive validation with custom error messages
- **Error Handling**: Global exception handling with structured error responses
- **Clean Architecture**: Separation of concerns with controllers, services, and models
- **Comprehensive Testing**: Unit and integration tests with JUnit 5

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Maven**
- **Spring Validation** (for input validation)

## API Endpoints

### Calculate Checkout Total
```
POST /api/v1/checkout/calculate
```

**Request Body:**
```json
{
  "cartItems": [
    {
      "sku": "A",
      "quantity": 3
    },
    {
      "sku": "B",
      "quantity": 2
    }
  ]
}
```

**Response:**
```json
{
  "originalTotal": 210,
  "discountAmount": 30,
  "finalTotal": 180,
  "cartItems": [...],
  "appliedPromotions": [
    {
      "promotionType": "BULK_DISCOUNT",
      "description": "Buy 3 Product A for $130",
      "discountAmount": 20,
      "affectedSkus": ["A"]
    }
  ]
}
```

**Error Response:**
```json
{
  "error": "VALIDATION_ERROR",
  "message": "Request validation failed",
  "status": 400,
  "timestamp": "2024-01-15T10:30:00",
  "details": {
    "cartItems[0].quantity": "Quantity must be at least 1"
  }
}
```

### Get Available SKUs
```
GET /api/v1/checkout/skus
```

**Response:**
```json
{
  "A": 50,
  "B": 30,
  "C": 20,
  "D": 15
}
```

## Available SKUs

| SKU | Product | Unit Price |
|-----|---------|------------|
| A   | Product A | $50 |
| B   | Product B | $30 |
| C   | Product C | $20 |
| D   | Product D | $15 |

## Promotional Rules

### Bulk Promotions
- **SKU A**: Buy 3 for $130 (instead of $150)
- **SKU B**: Buy 2 for $45 (instead of $60)

### Combo Promotions
- **SKU C + SKU D**: Buy together for $30 (instead of $35)

## Validation Rules

- **SKU**: Required field, must be one of: A, B, C, D
- **Quantity**: Must be at least 1
- **Cart Items**: Maximum 100 items per cart
- **Request Body**: Must be valid JSON with required fields

## Running the Application

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Build and Run
```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`



## Testing

Run the test suite:
```bash
mvn test
```

### Example Test Scenarios

1. **Scenario A**: 1 × A, 1 × B, 1 × C = $100
2. **Scenario B**: 5 × A, 5 × B, 1 × C = $370
3. **Scenario C**: 3 × A, 5 × B, 1 × C, 1 × D = $280

## Project Structure

```
src/main/java/com/promotion/
├── config/
│   └── PromotionConfig.java
├── controller/
│   └── CheckoutController.java
├── dto/
│   ├── CartItemDto.java
│   ├── CheckoutRequest.java
│   └── CheckoutResponse.java
├── exception/
│   └── GlobalExceptionHandler.java
├── model/
│   ├── CartItem.java
│   └── SKU.java
├── promotion/
│   ├── Promotion.java
│   ├── BulkPromotion.java
│   ├── ComboPromotion.java
│   └── PercentagePromotion.java
└── service/
    ├── CheckoutService.java
    └── PromotionEngine.java
```

## Configuration

The application uses `application.yml` for configuration:

- **Server Port**: 8080
- **Logging**: Configured for console and file output
- **Validation**: Input validation with custom error messages

## Development

The codebase follows a clean, layered architecture:

- **Controllers**: Handle HTTP requests and responses with validation
- **Services**: Contain business logic and promotion engine
- **Models**: Data structures and domain objects
- **DTOs**: Data transfer objects for API communication
- **Promotions**: Extensible promotion rule implementations
- **Exception Handling**: Global exception handler with structured error responses
- **Configuration**: Spring configuration for promotion rules

## Extending the System

To add new promotion types:

1. Implement the `Promotion` interface
2. Add the new promotion to the Spring configuration
3. The promotion engine will automatically apply it based on priority

## License

This project is for educational and demonstration purposes.
