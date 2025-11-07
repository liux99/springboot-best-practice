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

### 10. API Documentation (Swagger)
```
This project uses Springdoc OpenAPI to generate Swagger UI.

Swagger UI: http://localhost:8080/swagger-ui/index.html

OpenAPI JSON: http://localhost:8080/v3/api-docs

All controllers and DTOs are annotated with:

@Operation(summary = "Create new order")
@ApiResponse(...)
```
### 11. Observability: Actuator + Prometheus + Grafana
```
This microservice exposes internal telemetry via:

Spring Boot Actuator

/actuator/health – health checks

/actuator/metrics – JVM, HTTP metrics

/actuator/prometheus – scrape endpoint for Prometheus

Prometheus

Pulls metrics from /actuator/prometheus

URL: http://localhost:9090

Grafana

Visual dashboards based on Prometheus metrics

URL: http://localhost:3000

Default login: admin / admin
```

### 12. Logging Strategy 
```
Uses Logback with logstash-logback-encoder for JSON logs

Logs include:

timestamp, level, thread, logger, message

Custom fields: service, traceId, spanId

Example Log Format:
{
  "timestamp": "2025-11-05T10:00:00Z",
  "level": "INFO",
  "service": "order-service",
  "traceId": "f3b6c97e23",
  "spanId": "2a89ce01a1",
  "message": "Creating order for customer: Alice"
}

AOP can be used for logging entry/exit of methods

Manual log.info() used for key business actions
```
### 13. Observability Platform Integration (e.g., New Relic)
```
This project is ready to export logs and metrics to New Relic:

Java agent injected via Dockerfile

Logs in JSON format for ingestion

Optional: add OpenTelemetry SDK or New Relic Micrometer exporter

Traces and logs will include traceId and spanId

Configure New Relic agent via JAVA_OPTS:

-javaagent:/app/newrelic/newrelic.jar
-Dnewrelic.config.license_key=YOUR_KEY
-Dnewrelic.config.app_name=order-service

Optional exporters supported:

OTLP to New Relic, Splunk, or Tempo

Micrometer registry for New Relic
```

 

## Todos

* Product, Inventory, Delivery microservices
* Distributed transactions / Saga orchestration
* OAuth2 with Spring Security
* Kubernetes deployment

---

## License

MIT License

---

## Author

Eric Liu — [ericliux@yahoo.com](mailto:ericliux@yahoo.com)

Feel free to fork, extend, and use it as a blueprint for your own enterprise-ready microservices.
