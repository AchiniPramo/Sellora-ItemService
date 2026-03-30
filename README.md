# Item Service

A **microservice for managing e-commerce product catalog** built with **Spring Boot 4.0.3** and **MongoDB**. Item Service handles all product-related operations including creation, retrieval, updating, and deletion of items with support for product images, categories, pricing, and inventory management.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Data Model](#data-model)
- [API Endpoints](#api-endpoints)
- [Dependencies](#dependencies)
- [Setup & Installation](#setup--installation)
- [Build & Running](#build--running)
- [Database Management](#database-management)
- [Configuration](#configuration)
- [Development Guide](#development-guide)
- [Troubleshooting](#troubleshooting)

---

## 🎯 Overview

**Item Service** is responsible for managing the product catalog in the NexaShopping platform. It provides comprehensive product management capabilities including:

- ✅ **Create items** with name, description, price, category, and images
- ✅ **Retrieve items** individually or as a complete catalog
- ✅ **Update item information** including inventory and pricing
- ✅ **Delete items** from the catalog
- ✅ **MongoDB integration** for flexible document storage
- ✅ **Service discovery** via Netflix Eureka
- ✅ **Centralized configuration** via Spring Cloud Config
- ✅ **Automatic health checks** via Spring Actuator

**Service Name**: `item-service`  
**Port**: `8002`  
**Database**: MongoDB (Port 27017)  
**Service Registry**: Eureka (Port 9001)  
**Configuration Server**: Config Server (Port 9000)

---

## 🛠️ Tech Stack

### Spring Framework
| Component | Version | Purpose |
|-----------|---------|---------|
| Spring Boot | 4.0.3 | Application framework |
| Spring Cloud | 2025.1.0 | Cloud microservices orchestration |
| Spring Data MongoDB | Latest | MongoDB integration and ORM |
| Spring Validation | Latest | Bean validation framework |

### Microservices Components
| Component | Purpose |
|-----------|---------|
| **Spring Cloud Netflix Eureka Client** | Service discovery and registration |
| **Spring Cloud Config Client** | Centralized configuration management |
| **Spring Boot Actuator** | Health checks and monitoring endpoints |

### Development Tools
| Tool | Purpose |
|------|---------|
| **MapStruct 1.6.3** | DTO-to-Entity mapping |
| **Lombok** | Boilerplate code reduction |
| **SLF4J/Logback** | Logging |

### Database
| Technology | Version | Purpose |
|-----------|---------|---------|
| **MongoDB** | 4.4+ | NoSQL document database |
| **MongoDB Java Driver** | Latest | Java MongoDB client |

### Build & Deployment
| Tool | Purpose |
|------|---------|
| **Maven** | Build automation |
| **Java** | 21 (LTS) - Language runtime |

---

## 🏗️ Architecture

### System Architecture Diagram

```
┌─────────────────────────────────────────────────────────┐
│                   API Gateway                           │
│                  (Port 7000)                            │
│     Routes requests to microservices                   │
└──────────────────┬──────────────────────────────────────┘
                   │
        ┌──────────▼──────────┐
        │   Item Service      │
        │   (Port 8002)       │
        │ ┌──────────────┐    │
        │ │ ItemController   │
        │ │ /api/v1/items   │
        │ └─────┬────────┘    │
        │       │             │
        │ ┌─────▼────────┐    │
        │ │ ItemService  │    │
        │ │ Business Logic   │
        │ └─────┬────────┘    │
        │       │             │
        │ ┌─────▼──────────┐  │
        │ │ ItemRepository │  │
        │ │ Data Access    │  │
        │ └─────┬──────────┘  │
        └───────┼──────────────┘
                │
        ┌───────▼──────────┐
        │  MongoDB         │
        │  (Port 27017)    │
        │  items collection│
        └──────────────────┘

Service Discovery Integration:
┌──────────────────┐         ┌──────────────────┐
│ Item Service     │◄────────┤ Eureka Registry  │
│ Eureka Client    │         │ (Port 9001)      │
└──────────────────┘         └──────────────────┘

Configuration Management:
┌──────────────────┐         ┌──────────────────┐
│ Item Service     │────────►│ Config Server    │
│ Config Client    │         │ (Port 9000)      │
└──────────────────┘         └──────────────────┘
```

### Service Interaction Flow

```
1. API Gateway receives request
   ↓
2. Routes to Item Service at http://item-service/api/v1/items
   (Service discovery via Eureka)
   ↓
3. ItemController processes request
   ↓
4. ItemService executes business logic
   ↓
5. ItemRepository interacts with MongoDB
   ↓
6. MongoDB returns data
   ↓
7. Response mapped via MapStruct (DTO)
   ↓
8. Response sent back through API Gateway
```

---

## 📁 Project Structure

```
item-service/
├── src/
│   ├── main/
│   │   ├── java/lk/ijse/eca/itemservice/
│   │   │   ├── ItemServiceApplication.java      # Spring Boot entry point
│   │   │   │
│   │   │   ├── controller/
│   │   │   │   └── ItemController.java          # REST endpoints
│   │   │   │
│   │   │   ├── service/
│   │   │   │   ├── ItemService.java             # Service interface
│   │   │   │   └── impl/
│   │   │   │       └── ItemServiceImpl.java      # Business logic
│   │   │   │
│   │   │   ├── repository/
│   │   │   │   └── ItemRepository.java          # MongoDB data access
│   │   │   │
│   │   │   ├── entity/
│   │   │   │   └── Item.java                    # MongoDB document entity
│   │   │   │
│   │   │   ├── dto/
│   │   │   │   └── ItemDto.java                 # Data transfer object
│   │   │   │
│   │   │   ├── mapper/
│   │   │   │   └── ItemMapper.java              # MapStruct mapper
│   │   │   │
│   │   │   ├── exception/
│   │   │   │   ├── ItemNotFoundException.java   # Item not found error
│   │   │   │   └── ...other exceptions
│   │   │   │
│   │   │   └── handler/
│   │   │       └── GlobalExceptionHandler.java  # Global error handling
│   │   │
│   │   └── resources/
│   │       ├── application.yaml                 # Main configuration
│   │       └── application-dev.yaml             # Dev profile
│   │
│   └── test/
│       └── java/...                             # Test classes
│
├── target/
│   ├── classes/                                 # Compiled classes
│   └── generated-sources/                       # MapStruct generated code
│
├── pom.xml                                      # Maven configuration
├── mvnw & mvnw.cmd                             # Maven wrapper scripts
└── README.md                                    # This file
```

---

## 📊 Data Model

### Item Document (MongoDB)

**Collection Name**: `items`

```javascript
{
  "_id": "ITEM001",           // Item ID (Primary Key)
  "itemId": "ITEM001",        // Display item ID
  "name": "Wireless Earbuds", // Product name
  "shortDescription": "Premium audio experience", // Card display description
  "description": "High-quality wireless earbuds with noise cancellation...", // Full description
  "price": 5499.99,           // Price in LKR
  "category": "Electronics",  // Product category
  "stock": 45,                // Available quantity
  "images": [                 // Base64-encoded images (up to 4)
    "data:image/jpeg;base64,...",
    "data:image/jpeg;base64,..."
  ],
  "createdAt": "2024-03-19T10:30:00Z" // ISO 8601 timestamp
}
```

### Item Entity Class

```java
@Document(collection = "items")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Item {
    
    @Id
    private String itemId;                    // Unique identifier
    
    private String name;                      // Product display name
    private String shortDescription;          // Short tagline
    private String description;               // Full rich description
    
    private Double price;                     // Price in LKR
    private String category;                  // Category (Electronics, Food, etc.)
    private Integer stock;                    // Stock count
    
    private List<String> images;              // Base64-encoded product images
    private String createdAt;                 // Creation timestamp (ISO 8601)
}
```

### ItemDto (Data Transfer Object)

```java
@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ItemDto {
    
    @NotBlank(message = "Item ID is required")
    @Pattern(regexp = "^[A-Z]+$", message = "Item ID must be uppercase letters")
    private String itemId;
    
    @NotBlank(message = "Item name is required")
    private String name;
    
    @NotBlank(message = "Short description is required")
    private String shortDescription;
    
    @NotBlank(message = "Full description is required")
    private String description;
    
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;
    
    @NotBlank(message = "Category is required")
    private String category;
    
    @NotNull(message = "Stock is required")
    @PositiveOrZero(message = "Stock must be zero or positive")
    private Integer stock;
    
    @Size(max = 4, message = "Maximum 4 images allowed")
    private List<String> images;
    
    private String createdAt;
}
```

### Database Schema

**MongoDB Collection Structure** (items):

| Field | Type | Required | Constraints | Purpose |
|-------|------|----------|-------------|---------|
| `_id` | ObjectId | ✅ | Auto-generated | MongoDB primary key |
| `itemId` | String | ✅ | Uppercase letters | Unique item identifier |
| `name` | String | ✅ | Max 255 chars | Product display name |
| `shortDescription` | String | ✅ | Max 100 chars | Card display description |
| `description` | String | ✅ | Max 2000 chars | Full product details |
| `price` | Double | ✅ | Positive number | Price in LKR |
| `category` | String | ✅ | Max 50 chars | Product category |
| `stock` | Integer | ✅ | >= 0 | Available quantity |
| `images` | Array[String] | ❌ | Max 4 items | Base64-encoded images |
| `createdAt` | String | ❌ | ISO 8601 format | Creation timestamp |

---

## 🔌 API Endpoints

### Base URL
```
http://localhost:7000/api/v1/items
```
(Routed through API Gateway from Item Service at http://localhost:8002)

### 1. Create Item

**Endpoint**: `POST /api/v1/items`

**Request**:
```bash
curl -X POST http://localhost:7000/api/v1/items \
  -H "Content-Type: application/json" \
  -d '{
    "itemId": "ITEM001",
    "name": "Wireless Earbuds Pro",
    "shortDescription": "Premium audio experience",
    "description": "High-quality wireless earbuds with active noise cancellation and 24-hour battery life",
    "price": 5499.99,
    "category": "Electronics",
    "stock": 50,
    "images": [
      "data:image/jpeg;base64,/9j/4AAQSkZJRg...",
      "data:image/jpeg;base64,/9j/4AAQSkZJRg..."
    ]
  }'
```

**Response** (201 Created):
```json
{
  "itemId": "ITEM001",
  "name": "Wireless Earbuds Pro",
  "shortDescription": "Premium audio experience",
  "description": "High-quality wireless earbuds with active noise cancellation and 24-hour battery life",
  "price": 5499.99,
  "category": "Electronics",
  "stock": 50,
  "images": [...],
  "createdAt": "2024-03-19T10:35:00Z"
}
```

### 2. Get Single Item

**Endpoint**: `GET /api/v1/items/{itemId}`

**Request**:
```bash
curl -X GET http://localhost:7000/api/v1/items/ITEM001 \
  -H "Accept: application/json"
```

**Response** (200 OK):
```json
{
  "itemId": "ITEM001",
  "name": "Wireless Earbuds Pro",
  "shortDescription": "Premium audio experience",
  "description": "High-quality wireless earbuds...",
  "price": 5499.99,
  "category": "Electronics",
  "stock": 50,
  "images": [...],
  "createdAt": "2024-03-19T10:35:00Z"
}
```

**Error Response** (404 Not Found):
```json
{
  "status": 404,
  "message": "Item with ID 'ITEM999' not found",
  "timestamp": "2024-03-19T10:35:30Z"
}
```

### 3. Get All Items

**Endpoint**: `GET /api/v1/items`

**Request**:
```bash
curl -X GET http://localhost:7000/api/v1/items \
  -H "Accept: application/json"
```

**Response** (200 OK):
```json
[
  {
    "itemId": "ITEM001",
    "name": "Wireless Earbuds Pro",
    "shortDescription": "Premium audio experience",
    "price": 5499.99,
    "category": "Electronics",
    "stock": 50,
    "images": [...],
    "createdAt": "2024-03-19T10:35:00Z"
  },
  {
    "itemId": "ITEM002",
    "name": "USB-C Cable",
    "shortDescription": "Durable 2-meter cable",
    "price": 299.99,
    "category": "Electronics",
    "stock": 150,
    "images": [...],
    "createdAt": "2024-03-19T11:00:00Z"
  }
]
```

### 4. Update Item

**Endpoint**: `PUT /api/v1/items/{itemId}`

**Request**:
```bash
curl -X PUT http://localhost:7000/api/v1/items/ITEM001 \
  -H "Content-Type: application/json" \
  -d '{
    "itemId": "ITEM001",
    "name": "Wireless Earbuds Pro Max",
    "shortDescription": "Ultimate audio experience",
    "description": "Enhanced version with better noise cancellation",
    "price": 6499.99,
    "category": "Electronics",
    "stock": 40,
    "images": [...]
  }'
```

**Response** (200 OK):
```json
{
  "itemId": "ITEM001",
  "name": "Wireless Earbuds Pro Max",
  "shortDescription": "Ultimate audio experience",
  "price": 6499.99,
  "category": "Electronics",
  "stock": 40,
  "images": [...],
  "createdAt": "2024-03-19T10:35:00Z"
}
```

### 5. Delete Item

**Endpoint**: `DELETE /api/v1/items/{itemId}`

**Request**:
```bash
curl -X DELETE http://localhost:7000/api/v1/items/ITEM001
```

**Response** (204 No Content):
```
(Empty body)
```

**Error Response** (404 Not Found):
```json
{
  "status": 404,
  "message": "Item with ID 'ITEM999' not found",
  "timestamp": "2024-03-19T10:35:30Z"
}
```

---

## 📦 Dependencies

### Core Spring Boot Dependencies

```xml
<!-- Spring Data MongoDB - NoSQL database -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>

<!-- Spring MVC - REST API support -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webmvc</artifactId>
</dependency>

<!-- Validation - Bean validation framework -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

<!-- Actuator - Health checks and monitoring -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

### Microservices & Cloud Dependencies

```xml
<!-- Netflix Eureka Client - Service discovery -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>

<!-- Spring Cloud Config Client - Centralized configuration -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

### Development & Utility Dependencies

```xml
<!-- Lombok - Reduce boilerplate code -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>

<!-- MapStruct - DTO/Entity mapping -->
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>1.6.3</version>
</dependency>

<!-- Spring Boot DevTools - Auto-reload during development -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

### Version Properties

```xml
<java.version>21</java.version>
<mapstruct.version>1.6.3</mapstruct.version>
<spring-cloud.version>2025.1.0</spring-cloud.version>
```

---

## 🚀 Setup & Installation

### Prerequisites

- **Java 21** (JDK installed and in PATH)
- **Maven 3.8+** (or use ./mvnw wrapper)
- **MongoDB 4.4+** (running on port 27017)
- **Config Server** (running on port 9000)
- **Service Registry** (running on port 9001)

### Step 1: Clone Repository

```bash
cd NexaShopping-Project
cd services
cd item-service
```

### Step 2: Verify Java & Maven

```bash
java -version      # Should show Java 21
mvn --version      # Should show Maven 3.8+
```

### Step 3: Install Dependencies

```bash
# Using Maven wrapper (no Maven installation needed)
./mvnw clean install

# Or using installed Maven
mvn clean install
```

**Expected Output**:
```
[INFO] BUILD SUCCESS
[INFO] Total time: 35.421 s
[INFO] Finished at: 2024-03-19T10:30:00Z
```

### Step 4: Verify MongoDB Connection

```bash
# Test MongoDB connectivity
# Connect to MongoDB shell
mongo mongodb://localhost:27017/item-db

# Or using MongoDB CLI
mongosh "mongodb://localhost:27017/item-db"

# You should see:
# Current Mongosh Log ID: ...
# Connecting to: mongodb://localhost:27017/item-db
```

---

## ▶️ Build & Running

### Development Mode (with auto-reload)

```bash
./mvnw spring-boot:run
```

**Expected Output**:
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v4.0.3)

2024-03-19T10:30:00.000Z  INFO 12345 --- [           main] l.i.e.i.ItemServiceApplication          : Starting ItemServiceApplication
2024-03-19T10:30:05.000Z  INFO 12345 --- [           main] o.s.d.m.r.LocalMongoDbFactoryBean       : Initialized MongoDatabase
2024-03-19T10:30:08.000Z  INFO 12345 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8002 (http)
2024-03-19T10:30:10.000Z  INFO 12345 --- [           main] l.i.e.i.ItemServiceApplication          : Started ItemServiceApplication in 10.235 seconds
```

### Production Build

```bash
# Build JAR file
./mvnw clean package

# Expected JAR location
target/Item-Service-1.0.0.jar
```

### Run JAR File

```bash
# Direct execution
java -jar target/Item-Service-1.0.0.jar

# With custom port (if needed)
java -jar target/Item-Service-1.0.0.jar --server.port=8002
```

### Run with Maven Profiles

```bash
# Development profile
./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"

# Production profile
./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=prod"
```

### Verify Service is Running

```bash
# Check health endpoint
curl -X GET http://localhost:8002/actuator/health

# Expected response:
# {"status":"UP"}

# Check service registered in Eureka
curl -X GET http://localhost:9001/eureka/apps

# Should show item-service listed
```

---

## ⚙️ Configuration

### Application Configuration (application.yaml)

```yaml
spring:
  application:
    name: item-service
  
  # MongoDB Configuration
  data:
    mongodb:
      uri: mongodb://localhost:27017/item-db
      auto-index-creation: true
  
  # Config Server Integration
  config:
    import: "configserver:"
  
  cloud:
    config:
      uri: http://localhost:9000
      fail-fast: true
      retry:
        initial-interval: 2000
        max-interval: 10000
        multiplier: 1.1
        max-attempts: 6

# Server Configuration
server:
  port: 8002
  servlet:
    context-path: /

# Eureka Discovery Configuration
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:9001/eureka
    instance:
      leaseRenewalIntervalInSeconds: 30
      leaseExpirationDurationInSeconds: 90
  instance:
    preferIpAddress: false
    hostname: localhost

# Actuator Configuration
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
  endpoint:
    health:
      show-details: when-authorized

# Logging Configuration
logging:
  level:
    root: INFO
    lk.ijse.eca.itemservice: DEBUG
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} - %logger{36} - %msg%n"
```

### Development Profile (application-dev.yaml)

```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/item-db-dev
      auto-index-creation: true

server:
  port: 8002

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:9001/eureka

logging:
  level:
    lk.ijse.eca.itemservice: DEBUG
    org.springframework: DEBUG
```

### Environment Variables

```bash
# MongoDB
MONGO_USERNAME=admin
MONGO_PASSWORD=password
MONGO_DATABASE=item-db
MONGO_HOST=localhost
MONGO_PORT=27017

# Spring Cloud
CONFIG_SERVER_URL=http://localhost:9000
EUREKA_SERVER_URL=http://localhost:9001/eureka

# Application
SERVER_PORT=8002
LOGGING_LEVEL=INFO
```

---

## 📝 Development Guide

### Adding a New Field to Item

**1. Update Item Entity** (`entity/Item.java`):

```java
@Document(collection = "items")
public class Item {
    // ... existing fields ...
    
    // Add new field
    @Field("discount_percentage")
    private Double discountPercentage;
}
```

**2. Update ItemDto** (`dto/ItemDto.java`):

```java
public class ItemDto {
    // ... existing fields ...
    
    @PositiveOrZero(message = "Discount must be 0 or positive")
    private Double discountPercentage;
}
```

**3. Update ItemMapper** (`mapper/ItemMapper.java`):

```java
@Mapper(componentModel = "spring")
public interface ItemMapper {
    ItemDto toDto(Item entity);
    Item toEntity(ItemDto dto);
}
// MapStruct will automatically handle the new field
```

**4. Rebuild**:

```bash
./mvnw clean install
```

### Adding a New Endpoint

**1. Create method in ItemService interface**:

```java
public interface ItemService {
    List<ItemDto> getItemsByCategory(String category);
}
```

**2. Implement in ItemServiceImpl**:

```java
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository repository;
    
    public List<ItemDto> getItemsByCategory(String category) {
        return repository.findByCategory(category)
            .stream()
            .map(mapper::toDto)
            .collect(Collectors.toList());
    }
}
```

**3. Add endpoint in ItemController**:

```java
@GetMapping("/category/{category}")
public ResponseEntity<List<ItemDto>> getItemsByCategory(
        @PathVariable String category) {
    return ResponseEntity.ok(itemService.getItemsByCategory(category));
}
```

**4. Test the endpoint**:

```bash
curl -X GET http://localhost:7000/api/v1/items/category/Electronics
```

### Adding Custom Validation

```java
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidCategoryValidator.class)
public @interface ValidCategory {
    String message() default "Invalid category";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

public class ValidCategoryValidator implements ConstraintValidator<ValidCategory, String> {
    private static final Set<String> VALID_CATEGORIES = Set.of(
        "Electronics", "Food", "Clothing", "Books"
    );
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || VALID_CATEGORIES.contains(value);
    }
}

// Use in ItemDto
public class ItemDto {
    @ValidCategory
    private String category;
}
```

---

## 🐛 Troubleshooting

### Issue 1: MongoDB Connection Failed

**Error Message**:
```
org.springframework.data.mongodb.UncategorizedMongoDbException: 
Exception authenticating MongoClient
```

**Solutions**:

1. **Verify MongoDB is running**:
```bash
# macOS/Linux
brew services list | grep mongodb

# Windows (if installed as service)
net start MongoDB

# Or check if running
mongo --version
mongosh --version
```

2. **Check connection string**:
```yaml
spring:
  data:
    mongodb:
      uri: mongodb://username:password@localhost:27017/item-db
```

3. **Create database and user** (if needed):
```javascript
use admin
db.createUser({
  user: "root",
  pwd: "password",
  roles: [{role: "root", db: "admin"}]
})

use item-db
db.createCollection("items")
```

### Issue 2: Service Not Registering in Eureka

**Error Message**:
```
Failed to fetch service instance
Could not connect to service-registry
```

**Solutions**:

1. **Verify Eureka Server is running**:
```bash
curl -X GET http://localhost:9001/eureka
```

2. **Check eureka.client configuration**:
```yaml
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:9001/eureka
```

3. **Verify service name** (must match in all configs):
```yaml
spring:
  application:
    name: item-service
```

4. **Check logs for registration**:
```
DiscoveryClient_ITEM-SERVICE/... : registering service...
Updating renewalThresholdPercentage to 0.85
```

### Issue 3: Config Server Connection Timeout

**Error Message**:
```
Failed to fetch config from http://localhost:9000
Connection refused
```

**Solutions**:

1. **Verify Config Server is running** on port 9000

2. **Increase connection timeout** in `application.yaml`:
```yaml
spring:
  cloud:
    config:
      fail-fast: true
      retry:
        initial-interval: 2000
        max-interval: 10000
        max-attempts: 10
```

3. **Make Config Server optional**:
```yaml
spring:
  cloud:
    config:
      fail-fast: false
      enabled: true
```

### Issue 4: Port 8002 Already in Use

**Error Message**:
```
Caused by: java.net.BindException: Address already in use
```

**Solutions**:

```bash
# macOS/Linux - Find process using port 8002
lsof -i :8002

# Kill the process
kill -9 <PID>

# Windows - Find process using port 8002
netstat -ano | findstr :8002

# Kill the process
taskkill /PID <PID> /F
```

Or use different port:
```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments="--server.port=8003"
```

### Issue 5: Image/Document Size Exceeds MongoDB Limits

**Error Message**:
```
BsonSerializationException: Document size is too large
```

**Solutions**:

1. **Reduce image size** (Base64 encoded):
   - Compress images before encoding
   - Limit to 4 images max
   - Use reasonable image dimensions (e.g., 500x500px)

2. **Store images separately** (optional upgrade):
   - Use MongoDB GridFS for large files
   - Store image URLs instead of Base64
   - Use external storage (AWS S3, etc.)

3. **Check document size**:
```javascript
db.items.aggregate([
  {
    $project: {
      "docSize": {$bsonSize: "$$ROOT"}
    }
  }
]).pretty()
```

### Issue 6: Validation Failure

**Error Message**:
```
Item ID must contain uppercase letters only (A-Z)
```

**Solutions**:

1. **Use correct ItemId format**:
```json
{
  "itemId": "ITEM001",  // ✅ Correct
  "itemId": "Item001",  // ❌ Contains lowercase
  "itemId": "ITEM-001"  // ❌ Contains special chars
}
```

2. **Allowed pattern**: `^[A-Z]+$` (uppercase letters only, minimum 1)

### Issue 7: Image Data Not Preserved

**Problem**: Images uploaded successfully but appear corrupted when retrieved

**Solutions**:

1. **Verify Base64 encoding**:
```bash
# Check if Base64 is valid
echo "base64_string_here" | base64 -d > image.jpg

# Should produce valid image file
```

2. **Ensure proper MIME type**:
```
data:image/jpeg;base64,...
data:image/png;base64,...
data:image/webp;base64,...
```

3. **Check MongoDB size limits** for large images in Base64

---

## 📊 Monitoring & Health Checks

### Health Endpoint

```bash
curl -X GET http://localhost:8002/actuator/health
```

**Response**:
```json
{
  "status": "UP",
  "components": {
    "mongoDb": {"status": "UP"},
    "discoveryClient": {"status": "UP"},
    "diskSpace": {"status": "UP"}
  }
}
```

### Service Registry Status

```bash
curl -X GET http://localhost:9001/eureka/apps/item-service
```

### Metrics Endpoint

```bash
curl -X GET http://localhost:8002/actuator/metrics
```

---

## 🔐 Security Considerations

### Input Validation

- All DTOs use validation annotations (`@NotBlank`, `@Pattern`, `@Positive`)
- ItemId must be uppercase letters only
- Price must be positive
- Stock must be zero or positive
- Images limited to 4 per item

### Error Handling

- Global exception handler prevents information leakage
- Standard error response format for all endpoints
- Sensitive stack traces hidden in production

### MongoDB Security

- Use authentication credentials in production
- Restrict database user permissions
- Enable encryption in transit (TLS)
- Regular backups recommended

---

## 📚 References

- [Spring Data MongoDB Documentation](https://spring.io/projects/spring-data-mongodb)
- [Spring Boot Reference Guide](https://spring.io/projects/spring-boot)
- [Spring Cloud Documentation](https://spring.io/projects/spring-cloud)
- [MongoDB Documentation](https://docs.mongodb.com/)
- [MapStruct Documentation](https://mapstruct.org/)
- [Netflix Eureka](https://github.com/Netflix/eureka)

---

## 📝 Notes

- **Startup Sequence**: Config Server → Service Registry → Item Service → API Gateway
- **Service Discovery**: Eureka automatically handles service location
- **Configuration**: All properties can be overridden via Config Server
- **Database**: MongoDB auto-creates database and collection on first run
- **Timestamps**: Use ISO 8601 format for date/time fields

---

## 🤝 Support

For issues or questions:

1. Check the **[Troubleshooting](#troubleshooting)** section above
2. Review service-specific logs: `/logs/item-service.log`
3. Verify all **[Prerequisites](#prerequisites)** are met
4. Check **[Architecture](#architecture)** diagram for integration points
5. Review **[Configuration](#configuration)** section

---

**Last Updated**: March 19, 2026  
**Version**: 1.0.0  
**Service Name**: item-service  
**Port**: 8002  
**Database**: MongoDB 4.4+  
**Java**: 21+ .... meke name eka NexaShopping  wenuwata Sellora dala hadala denna english walin oni 
