# Online Quiz System

A Spring Boot REST API for an Online Quiz System with JWT-based authentication and role-based access control.

## Description

This application allows users to register, take quizzes, and view their results. Administrators can create and manage quizzes and users. Managers can review results and moderate quizzes.

## Team Members & Roles

| Name | Role |
|------|------|
| Developer | Backend (Spring Boot, Security, Database) |

## Tech Stack

- **Java 21** + **Spring Boot 3.5**
- **Spring Security** (JWT)
- **Spring Data JPA** + **PostgreSQL**
- **Springdoc OpenAPI** (Swagger UI)
- **Lombok**
- **JUnit 5** + **Mockito**

## API List

### Auth
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | `/auth/register` | Public | Register new user |
| POST | `/auth/login` | Public | Login and get JWT token |

### Quizzes
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/api/quizzes` | USER, MANAGER, ADMIN | Get all quizzes (supports `?search=` and `?category=`) |
| GET | `/api/quizzes/{id}` | USER, MANAGER, ADMIN | Get quiz by ID |
| POST | `/api/quizzes` | ADMIN | Create a new quiz |
| PUT | `/api/quizzes/{id}` | ADMIN | Update a quiz |
| DELETE | `/api/quizzes/{id}` | ADMIN | Delete a quiz |
| POST | `/api/quizzes/{id}/submit` | USER | Submit answers and get result |

### Results
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/api/results/my` | USER | Get current user's results |
| GET | `/api/results` | MANAGER, ADMIN | Get all results |
| GET | `/api/results/{id}` | MANAGER, ADMIN | Get result by ID |
| DELETE | `/api/results/{id}` | ADMIN | Delete a result |

### Users
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/api/users/me` | Authenticated | Get current user info |
| GET | `/api/users` | ADMIN | Get all users |
| PUT | `/api/users/{id}/role` | ADMIN | Change user role |
| DELETE | `/api/users/{id}` | ADMIN | Delete user |

## Roles

- **ROLE_USER** — Can register, take quizzes, view own results
- **ROLE_MANAGER** — Can view all results, search/filter quizzes
- **ROLE_ADMIN** — Full access: manage quizzes, users, results

## Database Tables

1. **users** — stores user accounts
2. **user_roles** — stores roles per user (joined to users)
3. **quizzes** — stores quiz metadata
4. **questions** — stores questions per quiz
5. **question_options** — stores answer choices per question
6. **quiz_results** — stores user quiz submission results

## Setup Instructions

### Prerequisites
- Java 21
- PostgreSQL running on port 5433
- Maven

### 1. Create Database
```sql
CREATE USER quiz_user WITH PASSWORD '95864235';
CREATE DATABASE quiz_db OWNER quiz_user;
GRANT ALL PRIVILEGES ON DATABASE quiz_db TO quiz_user;
```

### 2. Configure `application.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/quiz_db
spring.datasource.username=quiz_user
spring.datasource.password=95864235
```

### 3. Run
```bash
./mvnw spring-boot:run
```

### 4. Access Swagger UI
Open: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Default Admin Credentials
- **Username:** `admin`
- **Password:** `admin1234`

### Default Manager Credentials
- **Username:** `manager`
- **Password:** `manager1234`

## Testing

Run unit tests:
```bash
./mvnw test
```
