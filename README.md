# Sellora Item Service

A **microservice for managing the Sellora product catalog** built with **Spring Boot 4.0.3** and **MongoDB**. The Sellora Item Service handles all product-related operations including creation, retrieval, updating, and deletion of items with support for product images, categories, pricing, and inventory management.

-----

## 📋 Table of Contents

  - [Overview](https://www.google.com/search?q=%23overview)
  - [Tech Stack](https://www.google.com/search?q=%23tech-stack)
  - [Architecture](https://www.google.com/search?q=%23architecture)
  - [Project Structure](https://www.google.com/search?q=%23project-structure)
  - [Data Model](https://www.google.com/search?q=%23data-model)
  - [API Endpoints](https://www.google.com/search?q=%23api-endpoints)
  - [Dependencies](https://www.google.com/search?q=%23dependencies)
  - [Setup & Installation](https://www.google.com/search?q=%23setup--installation)
  - [Build & Running](https://www.google.com/search?q=%23build--running)
  - [Database Management](https://www.google.com/search?q=%23database-management)
  - [Configuration](https://www.google.com/search?q=%23configuration)
  - [Development Guide](https://www.google.com/search?q=%23development-guide)
  - [Troubleshooting](https://www.google.com/search?q=%23troubleshooting)

-----

## 🎯 Overview

**Sellora Item Service** is responsible for managing the product catalog within the Sellora e-commerce ecosystem. It provides comprehensive product management capabilities including:

  - ✅ **Create items** with name, description, price, category, and images
  - ✅ **Retrieve items** individually or as a complete catalog
  - ✅ **Update item information** including inventory and pricing
  - ✅ **Delete items** from the Sellora catalog
  - ✅ **MongoDB integration** for flexible document storage
  - ✅ **Service discovery** via Sellora Service Registry (Eureka)
  - ✅ **Centralized configuration** via Sellora Config Server
  - ✅ **Automatic health checks** via Spring Actuator

**Service Name**: `item-service`  
**Port**: `8002`  
**Database**: MongoDB (Port 27017)  
**Service Registry**: Eureka (Port 9001)  
**Configuration Server**: Config Server (Port 9000)

-----

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

-----

## 🏗️ Architecture

### Sellora System Architecture Diagram

```
┌─────────────────────────────────────────────────────────┐
│                    Sellora API Gateway                  │
│                        (Port 7000)                      │
│            Routes requests to Sellora microservices     │
└──────────────────┬──────────────────────────────────────┘
                   │
         ┌──────────▼──────────┐
         │  Sellora Item Service│
         │      (Port 8002)    │
         │ ┌──────────────┐    │
         │ │ ItemController  │
         │ │ /api/v1/items   │
         │ └─────┬────────┘    │
         │       │             │
         │ ┌─────▼────────┐    │
         │ │ ItemService  │    │
         │ │ Business Logic   │
         │ └─────┬────────┘    │
         │       │             │
         │ ┌─────▼──────────┐  │
         │ │ ItemRepository │  │
         │ │ Data Access    │  │
         │ └─────┬──────────┘  │
         └───────┼──────────────┘
                 │
         ┌───────▼──────────┐
         │     MongoDB      │
         │   (Port 27017)   │
         │  items collection│
         └──────────────────┘
```

-----

## 📊 Data Model

### Item Document (MongoDB)

**Collection Name**: `items`

```javascript
{
  "_id": "ITEM001",           // Sellora Item ID (Primary Key)
  "itemId": "ITEM001",        // Display item ID
  "name": "Sellora Wireless Earbuds", // Product name
  "shortDescription": "Premium audio experience", 
  "price": 5499.99,           // Price in LKR
  "category": "Electronics",  
  "stock": 45,                
  "images": [                 // Base64-encoded images
    "data:image/jpeg;base64,...",
    "data:image/jpeg;base64,..."
  ],
  "createdAt": "2026-03-19T10:30:00Z"
}
```

-----

## 🔌 API Endpoints

### Base URL

```
http://localhost:7000/api/v1/items
```

(Routed through the **Sellora API Gateway**)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/items` | Create a new Sellora product |
| GET | `/api/v1/items` | Retrieve the entire Sellora catalog |
| GET | `/api/v1/items/{itemId}` | Get details for a specific item |
| PUT | `/api/v1/items/{itemId}` | Update Sellora product details |
| DELETE | `/api/v1/items/{itemId}` | Remove an item from the catalog |

-----

## ⚙️ Configuration

### Sellora Application Configuration (application.yaml)

```yaml
spring:
  application:
    name: item-service
  
  # Sellora MongoDB Configuration
  data:
    mongodb:
      uri: mongodb://localhost:27017/sellora-item-db
      auto-index-creation: true
  
  # Sellora Config Server Integration
  config:
    import: "configserver:http://localhost:9000"

# Eureka Discovery Configuration
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:9001/eureka
```

-----

## 🚀 Setup & Installation

### Step 1: Clone Repository

```bash
cd Sellora-Project/services/item-service
```

### Step 2: Build & Run

```bash
# Install dependencies
./mvnw clean install

# Start Sellora Item Service
./mvnw spring-boot:run
```

-----

## 🤝 Support

For development issues:

1.  Review the **Sellora Service Interaction Flow** in the Architecture section.
2.  Check the logs at `/logs/sellora-item-service.log`.
3.  Ensure the **Sellora Service Registry** and **Config Server** are active.
