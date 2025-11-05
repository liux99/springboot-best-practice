# Spring Boot Microservice Best Practices

This project is a reference implementation of a **Spring Boot REST API microservice** designed to demonstrate **clean, modern, production-grade practices**. It uses a simple `Order` use case to illustrate how to properly structure, configure, and extend a microservice following enterprise standards.

---

## Project Goals

* ✅ Showcase **clean architecture principles** in Java/Spring Boot
* ✅ Provide a reference for **junior/mid-level developers** to learn by example
* ✅ Serve as a **base project** that can evolve into a microservice system (add `Product`, `Inventory`, `Delivery`, etc.)
* ✅ Encourage code that is **testable, modular, readable, and maintainable**

---

## Best Practices Tips

### 1. Clean Architecture Principles

**Layered separation** of:

* Controller (web API layer)
* Service (business logic)
* Repository (data access)
* DTOs and Entities (data model separation)

🧩 Code Sample:

```java
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping
    public ResponseEntity<Order> create(@Valid @RequestBody OrderRequest request) {
        return ResponseEntity.ok(service.createOrder(request));
    }
}
```

---

### 2. Startup with Spring Boot and Maven

Your `pom.xml` includes:

* Spring Boot starter dependencies
* Springdoc OpenAPI for Swagger
* Lombok for boilerplate reduction
* H2 for in-memory DB
* JUnit for tests

📄 See: [`pom.xml`](./pom.xml)

---

### 3. Standard Project Structure

```
src/main/java/com/example/orderservice
├── config        → Configuration beans (e.g., Swagger)
├── controller    → REST API endpoints
├── dto           → Input/output DTOs
├── entity        → JPA entities (mapped to DB)
├── exception     → Centralized error handling
├── repository    → Spring Data interfaces
└── service       → Business logic
```

---

### 4. Naming Conventions

* Classes: `PascalCase` (e.g., `OrderService`, `OrderRequest`)
* Variables: `camelCase`
* REST URLs: lowercase + hyphen (e.g., `/api/orders`)
* DTOs: `OrderRequest`, `OrderResponse`

---

### 5. Use Lombok to Reduce Boilerplate

Lombok is used for:

* `@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`
* `@RequiredArgsConstructor` for dependency injection

📌 Example:

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    private Long id;
    private String customerName;
    private Double amount;
}
```

---

### 6. DTO and Entity Separation

DTOs (like `OrderRequest`) are used to decouple the API from internal DB entities:

```java
public class OrderRequest {
    private String customerName;
    private Double amount;
}
```

Entities reflect database persistence logic:

```java
@Entity
@Table(name = "orders")
public class Order {
    private Long id;
    private String customerName;
    private Double amount;
    private LocalDateTime createdAt;
}
```

---

### 7. Dependency Injection via Constructor

Spring injects services using constructor-based injection:

```java
@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService service;
}
```

This makes the class testable and avoids field injection.

---

### 8. Bean Validation

We validate request payloads using `jakarta.validation.constraints`:

```java
public class OrderRequest {
    @NotBlank
    private String customerName;

    @DecimalMin("0.1")
    private Double amount;
}
```

Handled via:

```java
@PostMapping
public ResponseEntity<Order> create(@Valid @RequestBody OrderRequest request)
```

---

### 9. Centralized Exception Handling

We use `@RestControllerAdvice` to capture all errors and return user-friendly messages:

```java
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<Map<String, String>> handleValidationErrors(...) {
    // Return field-level error map
}
```

All handled in `GlobalExceptionHandler.java`

---

## Todos

* Product, Inventory, Delivery microservices
* Distributed transactions / Saga orchestration
* OAuth2 with Spring Security
* Actuator, metrics, and health checks
* Docker Compose and Kubernetes deployment

---

## License

MIT License

---

## Author

Eric Liu — [ericliux@yahoo.com](mailto:ericliux@yahoo.com)

Feel free to fork, extend, and use it as a blueprint for your own enterprise-ready microservices.
