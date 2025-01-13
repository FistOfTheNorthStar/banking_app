# banking_app
Banking App

# Testing
You will need to install Docker.

### Docker compose script
./start.sh

### Create account
curl -X POST "http://localhost:8070/api/accounts?customerId=1&initialCredit=100"

### Get user info
curl "http://localhost:8072/api/users/1/account-info"


# Banking Application

This project is a microservices-based banking application that allows for managing customer accounts and transactions. The system consists of three main services:
- Account Service
- Transaction Service
- User Service

## Features

- Create new accounts for existing customers
- Initialize accounts with initial credit
- Automatically create transactions for initial credit
- View user account information including balance and transactions
- RESTful API endpoints for all operations

## Architecture

The application is built using a microservices architecture with the following components:
(My port 8080 was taken for another application, but it is safe to replace "807")

- **Account Service (Port 8070)**
  - Manages account creation and retrieval
  - Communicates with Transaction Service for initial credit

- **Transaction Service (Port 8071)**
  - Handles all transaction-related operations
  - Records credit transactions for new accounts

- **User Service (Port 8072)**
  - Aggregates user information
  - Retrieves account and transaction details
  - Provides unified view of user's financial data

## Technologies

- Java 23
- Spring Boot 3.4.1
- Spring Cloud OpenFeign
- Gradle
- Docker
- JUnit 5
- Mockito

## Prerequisites

- Java 23 or higher
- Docker and Docker Compose
- Gradle 8.10 or higher

## Getting Started

1. Clone the repository:
```bash
git clone git@github.com:FistOfTheNorthStar/banking_app.git
cd banking-app
```

2. Build and run with Docker:
```bash
docker-compose up --build
```

3. Testing & rebuilding fast
```bash
./start.sh
```

## API Endpoints

### Account Service (8070)
```bash
# Create a new account
POST http://localhost:8070/api/accounts?customerId={id}&initialCredit={amount}

# Get account by ID
GET http://localhost:8070/api/accounts/{id}
```

### Transaction Service (8071)
```bash
# Get transactions for account
GET http://localhost:8071/api/transactions/account/{accountId}
```

### User Service (8072)
```bash
# Get user account information
GET http://localhost:8072/api/users/{userId}/account-info
```

## Testing

### Running Tests
```bash
# Run all tests
./gradlew test

# Run tests for specific service
./gradlew :account-service:test
./gradlew :transaction-service:test
./gradlew :user-service:test
```

## Project Structure
```
banking-app/
├── account-service/
│   ├── src/
│   ├── build.gradle
│   └── Dockerfile
├── transaction-service/
│   ├── src/
│   ├── build.gradle
│   └── Dockerfile
├── user-service/
│   ├── src/
│   ├── build.gradle
│   └── Dockerfile
├── docker-compose.yml
├── settings.gradle
└── README.md
```

## Configuration

Each service has its own application.properties file with the following default configurations:

### Account Service
```properties
server.port=8070
spring.application.name=account-service
transaction.service.url=${TRANSACTION_SERVICE_URL:http://transaction-service:8071}
```

### Transaction Service
```properties
server.port=8071
spring.application.name=transaction-service
```

### User Service
```properties
server.port=8072
spring.application.name=user-service
account.service.url=${ACCOUNT_SERVICE_URL:http://account-service:8070}
transaction.service.url=${TRANSACTION_SERVICE_URL:http://transaction-service:8071}
```

## Docker Support

The application is containerized using Docker. Each service has its own Dockerfile, and the services are orchestrated using docker-compose.

To run with Docker:
```bash
# Build and start all services
docker-compose up --build

# Stop all services
docker-compose down

# View logs
docker-compose logs -f [service-name]
```
