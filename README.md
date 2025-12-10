# E-commerce Backend API

A full-featured e-commerce backend application built with Spring Boot, featuring user authentication, product management, and order processing.

## Features

### Core Functionality
- **User Authentication & Authorization**: JWT-based secure authentication system
- **Product Management**: Complete CRUD operations for products
- **Order Processing**: Full order lifecycle management from creation to delivery

### Technology Stack
- Java 17
- Spring Boot 3.2.0
- Spring Security with JWT
- Spring Data JPA
- H2 Database (development)
- MySQL (production-ready)
- Maven

## Project Structure

```
src/main/java/com/ecommerce/api/
├── config/              # Security and application configuration
├── controller/          # REST API endpoints
├── dto/                 # Data Transfer Objects
├── entity/              # JPA entities
├── exception/           # Custom exceptions and global handler
├── repository/          # Data access layer
├── service/             # Business logic
└── util/                # Utility classes (JWT)
```

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Installation

1. Clone the repository
2. Navigate to the project directory
3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

The application will start at `http://localhost:8080/api`

### H2 Console
Access the H2 database console at: `http://localhost:8080/api/h2-console`
- JDBC URL: `jdbc:h2:mem:ecommercedb`
- Username: `sa`
- Password: (leave blank)

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and receive JWT token

### Products
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/category/{category}` - Get products by category
- `GET /api/products/search?keyword={keyword}` - Search products
- `POST /api/products` - Create new product (Admin only)
- `PUT /api/products/{id}` - Update product (Admin only)
- `DELETE /api/products/{id}` - Delete product (Admin only)

### Orders
- `POST /api/orders` - Create new order (Authenticated)
- `GET /api/orders/my-orders` - Get user's orders (Authenticated)
- `GET /api/orders/{id}` - Get order by ID (Authenticated)
- `GET /api/orders/number/{orderNumber}` - Get order by number (Authenticated)
- `GET /api/orders` - Get all orders (Admin only)
- `PATCH /api/orders/{id}/status?status={status}` - Update order status (Admin only)
- `POST /api/orders/{id}/cancel` - Cancel order (Authenticated)

## API Usage Examples

### Register User
```json
POST /api/auth/register
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "1234567890",
  "address": "123 Main St, City"
}
```

### Login
```json
POST /api/auth/login
{
  "username": "john_doe",
  "password": "password123"
}
```

### Create Product (Admin)
```json
POST /api/products
Authorization: Bearer {token}
{
  "name": "Laptop",
  "description": "High-performance laptop",
  "price": 999.99,
  "stockQuantity": 50,
  "category": "Electronics",
  "sku": "LAP-001",
  "imageUrl": "https://example.com/laptop.jpg"
}
```

### Create Order
```json
POST /api/orders
Authorization: Bearer {token}
{
  "items": [
    {
      "productId": 1,
      "quantity": 2
    }
  ],
  "shippingAddress": "123 Main St, City, State 12345",
  "paymentMethod": "Credit Card",
  "notes": "Please deliver in the morning"
}
```

## Security

- JWT tokens are required for authenticated endpoints
- Tokens expire after 24 hours
- Admin role required for product management and viewing all orders
- Passwords are encrypted using BCrypt

## Database Schema

### Main Entities
- **User**: User account information and authentication
- **Product**: Product catalog with pricing and inventory
- **Order**: Order header with status and delivery information
- **OrderItem**: Line items for each order with product details

## Configuration

Key configuration in `application.yml`:
- Server port: 8080
- Context path: /api
- JWT secret and expiration
- Database settings

## Error Handling

The API uses a global exception handler that returns consistent error responses:
```json
{
  "success": false,
  "message": "Error description",
  "data": null
}
```

## Future Enhancements

- Payment gateway integration
- Email notifications
- Product reviews and ratings
- Shopping cart functionality
- Inventory management
- Order tracking
- Admin dashboard

## License

This project is open source and available for educational purposes.
