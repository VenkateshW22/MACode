# User Management API

A secure, production-ready REST API for user management built with Spring Boot, Spring Security, and Spring Data JPA. This API provides comprehensive user management capabilities including authentication, authorization, and CRUD operations.

## Features

- 🔐 **JWT-based Authentication**
- 🔒 **Role-based Access Control (RBAC)**
- 📝 **User Registration & Management**
- 🔄 **Password Encryption** with BCrypt
- 📊 **Pagination & Sorting** for user listings
- ✅ **Input Validation**
- 📅 **Audit Fields** (createdAt, updatedAt)
- 🧪 **Comprehensive Test Coverage**

## Tech Stack

- **Java 17**
- **Spring Boot 3.1.0**
- **Spring Security**
- **Spring Data JPA**
- **H2 Database** (for development)
- **Maven**
- **JUnit 5** & **MockMvc** for testing

## Prerequisites

- Java 17 or higher
- Maven 3.6.3 or higher
- (Optional) Docker for containerization

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/usermanagementapi.git
cd usermanagementapi
```

### 2. Build the Application

```bash
mvn clean install
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Documentation

### Base URL
```
http://localhost:8080/api
```

### Authentication
All endpoints except `/api/auth/**` and `/api/users/register` are secured and require a valid JWT token.

### Available Endpoints

| Method | Endpoint | Description | Required Role |
|--------|----------|-------------|---------------|
| POST   | `/api/auth/authenticate` | Authenticate user and get JWT token | PUBLIC |
| POST   | `/api/users/register` | Register a new user | PUBLIC |
| GET    | `/api/users` | Get all users (paginated) | ADMIN |
| GET    | `/api/users/all` | Get all users (alternative endpoint) | ADMIN |
| GET    | `/api/users/{id}` | Get user by ID | USER (own profile) or ADMIN |
| PUT    | `/api/users/{id}` | Update user | USER (own profile) or ADMIN |
| DELETE | `/api/users/{id}` | Delete user | ADMIN |

## Example Requests

### 1. Register a New User

```http
POST /api/users/register
Content-Type: application/json

{
    "name": "John Doe",
    "email": "john.doe@example.com",
    "password": "SecurePass123!"
}
```

### 2. Authenticate

```http
POST /api/auth/authenticate
Content-Type: application/json

{
    "email": "john.doe@example.com",
    "password": "SecurePass123!"
}
```

### 3. Get All Users (Paginated)

```http
GET /api/users?page=0&size=10&sort=name,asc
Authorization: Bearer <your-jwt-token>
```

## Password Requirements

- At least 8 characters
- At least one digit (0-9)
- At least one lowercase letter (a-z)
- At least one uppercase letter (A-Z)
- At least one special character (!@#&()–[{}]:;',?/*~$^+=<>)

## Testing

Run all tests:

```bash
mvn test
```

## Security Considerations

- All passwords are hashed using BCrypt
- JWT tokens have a limited lifetime
- CSRF protection is enabled
- Role-based access control (RBAC) is implemented
- Input validation is performed on all endpoints

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [Project Lombok](https://projectlombok.org/)
