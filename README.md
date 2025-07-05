# Promotion Engine API

A simple Spring Boot API for calculating checkout totals with promotional rules.

## Overview

This application provides a REST API for calculating checkout totals with various promotional rules including bulk discounts and combo offers. The codebase is designed to be straightforward and maintainable.

## Features

- **Bulk Promotions**: Buy X quantity of a SKU for a special price
- **Combo Promotions**: Buy two specific SKUs together for a special price
- **REST API**: Simple endpoints for checkout calculation and SKU information
- **Validation**: Input validation for cart items
- **Clean Architecture**: Separation of concerns with controllers, services, and models

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Maven**
- **H2 Database** (for future persistence)

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

### Using Docker
```bash
# Build Docker image
docker build -t promotion-engine .

# Run container
docker run -p 8080:8080 promotion-engine
```

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
├── controller/
│   └── CheckoutController.java
├── dto/
│   ├── CartItemDto.java
│   ├── CheckoutRequest.java
│   └── CheckoutResponse.java
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
- **Actuator Endpoints**: Health and info endpoints available at `/actuator`
- **Logging**: Configured for console and file output

## Development

The codebase follows a simple, clean architecture:

- **Controllers**: Handle HTTP requests and responses
- **Services**: Contain business logic
- **Models**: Data structures
- **DTOs**: Data transfer objects for API communication
- **Promotions**: Extensible promotion rule implementations

## Extending the System

To add new promotion types:

1. Implement the `Promotion` interface
2. Add the new promotion to the Spring configuration
3. The promotion engine will automatically apply it based on priority

## License

This project is for educational and demonstration purposes.
