# 🎵 Light & Sound Sanctuary

A modern **microservices-based backend platform** for discovering, managing, and experiencing healing sound sanctuaries.

Built with **Java 17**, **Spring Boot 3**, **Spring Cloud Gateway**, **Spring Security**, **PostgreSQL**, and **Docker**.

---

## ✨ Overview

**Light & Sound Sanctuary** is a wellness-focused backend system designed to support a calming digital experience where users can explore sanctuary locations, access healing sound content, interact with beacon-based features, and receive notifications.

The backend is designed using a **microservices architecture**, where each service has a clear responsibility and communicates through a centralized API Gateway.

---

## 🏗️ Architecture

```text
Client / Frontend
        |
        v
Gateway Service : 8080
JWT Validation + Request Routing
        |
        |----> User Service : 8082
        |      PostgreSQL
        |
        |----> Media Service : 8083
        |      PostgreSQL + Local Storage
        |
        |----> Beacon Service : 8081
        |      Local Storage
        |
        |----> Notification Service : 8084
        |
        |----> Sanctuary Map Service : 8085
               PostgreSQL
```

---

## 🧩 Microservices

| Service | Port | Responsibility |
|---|---:|---|
| `gateway-service` | `8080` | Routes requests and validates JWT tokens |
| `user-service` | `8082` | User registration, login, Google OAuth2, and JWT generation |
| `media-service` | `8083` | Sound file upload, sound listing, and category management |
| `beacon-service` | `8081` | Beacon data upload and local storage |
| `notification-service` | `8084` | Notification handling |
| `sanctuary-map-service` | `8085` | Sanctuary location CRUD operations |

---

## 🚀 Key Features

- Secure user registration and login
- JWT-based authentication
- Google OAuth2 login support
- Gateway-level JWT validation
- Protected and public API routing
- BCrypt password hashing
- Sound file upload using local storage
- Sanctuary location CRUD operations
- Beacon data upload support
- Notification endpoint support
- Input validation
- Global exception handling
- PostgreSQL database integration
- Docker Compose database setup
- Maven multi-module backend structure

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.2.5 |
| API Gateway | Spring Cloud Gateway |
| Security | Spring Security, JWT, Google OAuth2 |
| Database | PostgreSQL |
| ORM | Spring Data JPA / Hibernate |
| Storage | Local File Storage |
| Build Tool | Maven |
| Containerization | Docker, Docker Compose |
| Utility | Lombok |

---

## 📁 Project Structure

```text
Light-sound-sanctuary/
│
└── back-end/
    └── lightsoundsanctuary/
        │
        ├── gateway-service/
        ├── user-service/
        ├── media-service/
        ├── beacon-service/
        ├── notification-service/
        ├── sanctuary-map-service/
        ├── docker-compose.yml
        └── pom.xml
```

---

## ⚙️ Prerequisites

Make sure the following tools are installed:

- Java 17
- Maven
- Docker Desktop
- IntelliJ IDEA or another Java IDE

---

## ▶️ Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/thahiranisha/Light-sound-sanctuary.git
cd Light-sound-sanctuary/back-end/lightsoundsanctuary
```

---

### 2. Create `.env` File

Create a `.env` file in the backend root directory.

```env
GOOGLE_CLIENT_ID=your-google-client-id
GOOGLE_CLIENT_SECRET=your-google-client-secret
JWT_SECRET=your-jwt-secret-key
```

---

### 3. Start Databases

```bash
docker-compose up userdb mediadb mapdb -d
```

Check running containers:

```bash
docker ps
```

---

### 4. Run Services

You can run each service from IntelliJ IDEA.

Or run manually using Maven:

```bash
cd user-service
./mvnw spring-boot:run
```

For Windows:

```bash
cd user-service
mvnw spring-boot:run
```

Run the required services:

```text
gateway-service
user-service
media-service
beacon-service
notification-service
sanctuary-map-service
```

---

## 🌐 API Gateway

All frontend requests should go through the Gateway Service.

```text
Base URL: http://localhost:8080
```

The gateway is responsible for:

- Routing requests to the correct microservice
- Validating JWT tokens
- Protecting secured API endpoints

---

## 🔐 Authentication APIs

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/users/register` | Register a new user |
| `POST` | `/api/users/login` | Login and receive JWT token |
| `GET` | `/oauth2/authorization/google` | Start Google OAuth2 login |

---

## 🔑 Protected Routes

Protected APIs require the following header:

```http
Authorization: Bearer <jwt-token>
```

Public routes:

```text
/api/users/register
/api/users/login
/oauth2/**
```

All other routes require a valid JWT token.

---

## 📍 Sanctuary Map APIs

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/map/sanctuaries` | Get all sanctuaries |
| `POST` | `/api/map/sanctuaries` | Add a new sanctuary |
| `GET` | `/api/map/sanctuaries/type/{type}` | Filter sanctuaries by type |

---

## 🎧 Media APIs

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/media/upload` | Upload a sound file |
| `GET` | `/api/media/sounds` | Get all sounds |

---

## 📡 Beacon APIs

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/beacons/upload` | Upload beacon data |

---

## 🔔 Notification APIs

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/notifications/send` | Send notification |

---

## 🛡️ Security

The backend includes several security features:

- JWT token generation during login
- JWT validation at gateway level
- BCrypt password hashing
- Google OAuth2 authentication
- Public and protected route separation
- Input validation
- Global exception handling

---

## 🧪 Example API Usage

### Login Request

```http
POST http://localhost:8080/api/users/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}
```

### Protected Request

```http
GET http://localhost:8080/api/media/sounds
Authorization: Bearer <jwt-token>
```

---

## 🐳 Docker Commands

Start databases:

```bash
docker-compose up -d
```

Stop containers:

```bash
docker-compose down
```

Stop and remove volumes:

```bash
docker-compose down -v
```

---

## 📊 Project Status

| Feature | Status |
|---|---|
| JWT Authentication | ✅ Complete |
| Google OAuth2 Login | ✅ Complete |
| API Gateway Filter | ✅ Complete |
| Media Upload with Local Storage | ✅ Complete |
| Sanctuary CRUD | ✅ Complete |
| Beacon Upload | ✅ Complete |
| Notification Endpoint | ✅ Complete |
| Input Validation | ✅ Complete |
| Global Exception Handler | ✅ Complete |
| Docker Compose Setup | ✅ Complete |
| Kafka Async Messaging | 🔄 Planned |
| CI/CD Pipeline | 🔄 Planned |
| Angular Frontend | 🔄 In Progress |
| AWS Deployment | 🔄 Planned |

---

## 🗺️ Future Enhancements

- Kafka-based asynchronous messaging
- Centralized logging
- CI/CD pipeline with GitHub Actions
- AWS deployment
- S3 file storage support
- Service discovery
- Monitoring with Prometheus and Grafana
- Role-based access control
- Angular frontend integration

---

## 👩‍💻 Author

**Thahira Nisha**  
Software Engineer

- GitHub: [thahiranisha](https://github.com/thahiranisha)
- LinkedIn: [Thahira Nisha](https://linkedin.com/in/thahira-nisha)

---

## 📄 License

This project is developed for academic and portfolio purposes.
