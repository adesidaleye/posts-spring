# Posts Spring API

A blog REST API built with Spring Boot, showing stateless authentication with **Spring Security and JWT**.

## Features

- Register and log in to receive a JWT
- Custom JWT filter that validates the token on every request
- BCrypt password hashing
- Ownership-based authorization: only a post's author can update or delete it
- Posts, categories, and tags
- Request validation and consistent JSON error responses

## Tech Stack

Java 21 · Spring Boot · Spring Security · Spring Data JPA · PostgreSQL · JWT · MapStruct · Lombok · Maven

## Getting Started

**1. Clone and start the database**

```bash
git clone https://github.com/adesidaleye/posts-spring.git
cd posts-spring
docker-compose up -d
```

**2. Configure `src/main/resources/application.properties`**

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/<your-db>
spring.datasource.username=<your-username>
spring.datasource.password=<your-password>
spring.jpa.hibernate.ddl-auto=update

# At least 32 characters
jwt.secret=<your-long-random-secret>
```

**3. Run**

```bash
./mvnw spring-boot:run
```

The API runs at `http://localhost:8080`.

## Authentication

1. `POST /api/v1/auth/register` or `POST /api/v1/auth/login` returns a JWT.
2. Send it with each protected request:

   ```
   Authorization: Bearer <token>
   ```

Tokens expire after 24 hours.

## Endpoints

| Method | Endpoint | Auth |
| :--- | :--- | :--- |
| `POST` | `/api/v1/auth/register` | Public |
| `POST` | `/api/v1/auth/login` | Public |
| `GET` | `/api/v1/posts`, `/api/v1/posts/{id}` | Public |
| `POST` | `/api/v1/posts` | Token |
| `PUT` `DELETE` | `/api/v1/posts/{id}` | Token (author only) |
| `GET` | `/api/v1/categories`, `/api/v1/tags` | Public |
| `POST` `DELETE` | `/api/v1/categories`, `/api/v1/tags` | Token |
