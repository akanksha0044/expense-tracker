# Expense Tracker API

A Spring Boot REST API for tracking personal expenses with JWT-based authentication.

## Tech Stack

- Java 21
- Spring Boot 3.5.6
- Spring Security + JWT (jjwt 0.11.5)
- Spring Data JPA
- MySQL
- Lombok

## Setup

1. Create a MySQL database named `demo`
2. Update credentials in `src/main/resources/application.properties` if needed
3. Run the app — tables are auto-created via `ddl-auto=update`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/demo
spring.datasource.username=root
spring.datasource.password=your_password
```

---

## API Endpoints

### Base URL: `http://localhost:8080`

---

## Auth APIs (No token required)

### Register

```bash
curl -X POST "http://localhost:8080/api/auth/register" \
  -H "Content-Type: application/json" \
  -d "{\"username\": \"akanksha\", \"password\": \"******\"}"
```

**Response:**
```
User Registered
```

---

### Login

```bash
curl -X POST "http://localhost:8080/api/auth/login" \
  -H "Content-Type: application/json" \
  -d "{\"username\": \"akanksha\", \"password\": \"akanksha@7\"}"
```

**Response:**
```
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJha2Fua3NoYSIsImV4cCI6...
```

> Copy this token — it expires in **1 hour**. Use it in all expense API calls.

---

## Expense APIs (Token required)

Replace `YOUR_TOKEN` with the token received from the login response.

---

### Add Expense

```bash
curl -X POST "http://localhost:8080/api/expenses" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d "{\"expenseName\": \"Food\", \"expenseCategory\": \"Lunch\", \"expenseAmount\": 200.00}"
```

**Response:**
```json
{
  "id": 1,
  "expenseName": "Food",
  "expenseCategory": "Lunch",
  "expenseAmount": 200.00,
  "user": {
    "id": 1,
    "username": "akanksha"
  }
}
```

---

### Get All Expenses

```bash
curl -X GET "http://localhost:8080/api/expenses" \
  -H "Authorization: Bearer YOUR_TOKEN"
```

**Response:**
```json
[
  {
    "id": 1,
    "expenseName": "Food",
    "expenseCategory": "Lunch",
    "expenseAmount": 200.00,
    "user": {
      "id": 1,
      "username": "akanksha"
    }
  }
]
```

---

## Windows CMD One-Liners

If you're on Windows CMD (not PowerShell or bash), use these single-line versions:

**Register:**
```
curl -X POST "http://localhost:8080/api/auth/register" -H "Content-Type: application/json" -d "{\"username\": \"akanksha\", \"password\": \"akanksha@7\"}"
```

**Login:**
```
curl -X POST "http://localhost:8080/api/auth/login" -H "Content-Type: application/json" -d "{\"username\": \"akanksha\", \"password\": \"akanksha@7\"}"
```

**Add Expense:**
```
curl -X POST "http://localhost:8080/api/expenses" -H "Authorization: Bearer YOUR_TOKEN" -H "Content-Type: application/json" -d "{\"expenseName\": \"Food\", \"expenseCategory\": \"Lunch\", \"expenseAmount\": 200.00}"
```

**Get Expenses:**
```
curl -X GET "http://localhost:8080/api/expenses" -H "Authorization: Bearer YOUR_TOKEN"
```

---

## Common Errors

| Error | Cause | Fix |
|-------|-------|-----|
| `400 Bad Request` | Missing `Content-Type: application/json` header | Add `-H "Content-Type: application/json"` |
| `401 Unauthorized` | Token expired or missing | Login again to get a fresh token |
| `500 Duplicate entry` | Username already registered | Use a different username or just login |
| `Invalid or expired token` | Token older than 1 hour | Login again to get a new token |

---

## Notes

- Tokens expire after **1 hour** — always use a fresh token
- Each user only sees their own expenses
- Passwords are stored as BCrypt hashes
