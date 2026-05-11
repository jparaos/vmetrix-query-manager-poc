# VMetrix Query Manager POC

Dynamic metadata-driven SQL query engine built with Spring Boot.

---

# Overview

This project implements a dynamic query engine capable of:

* Generating SQL dynamically from JSON requests
* Resolving entities and joins using metadata definitions
* Supporting recursive filter groups (AND / OR)
* Applying comparator strategies dynamically
* Producing parameterized SQL queries
* Validating requests before SQL generation
* Executing generated queries using JDBC

The solution was designed with a layered and extensible architecture focused on separation of responsibilities and maintainability.

---

# Tech Stack

* Java 17
* Spring Boot 2.7.x
* Spring JDBC
* H2 Database
* Maven
* Lombok
* Swagger / OpenAPI
* JUnit 5
* MockMvc

---

# Project Structure

```text
src/main/java/com/vmetrix/querymanager
│
├── api
├── config
├── execution
├── metadata
├── query
├── shared
├── sql
└── validation
```

---

# Architecture Flow

```text
HTTP Request
    ↓
Controller
    ↓
Query Engine
    ↓
Parser
    ↓
Validation
    ↓
Resolver
    ↓
SQL Builder
    ↓
SQL Generator
    ↓
Execution Layer
    ↓
Response Mapper
```

---

# Main Features

## Metadata-Driven Querying

Entities, fields, relationships, and comparators are loaded dynamically from JSON metadata files.

Example:

```text
src/main/resources/metadata
```

---

## Dynamic Join Resolution

Joins are automatically resolved based on entity relationships.

Example:

```sql
LEFT JOIN PARTY p
ON t.COUNTERPARTY_ID = p.PARTY_ID
```

---

## Recursive Filters

Supports nested filter groups:

```json
{
  "operator": "AND",
  "conditions": [
    {
      "entity": "transaction",
      "field": "amount",
      "comparator": "greaterThan",
      "value": 1000
    },
    {
      "operator": "OR",
      "conditions": []
    }
  ]
}
```

---

## Comparator Strategy Pattern

Comparators are implemented using strategy pattern.

Examples:

* equals
* greaterThan
* lessThan
* like
* between
* in
* isNull
* isNotNull

---

## Parameterized SQL

Generated SQL uses named parameters to prevent SQL injection.

Example:

```sql
WHERE t.AMOUNT > :p1
```

---

# Running the Application

## Build

```bash
mvn clean install
```

## Run

```bash
mvn spring-boot:run
```

Application runs on:

```text
http://localhost:8080
```

---

# Swagger UI

Swagger documentation:

```text
http://localhost:8080/swagger-ui/index.html
```

---

# H2 Console

H2 console:

```text
http://localhost:8080/h2-console
```

Connection:

```text
JDBC URL: jdbc:h2:mem:vmetrixdb
Username: sa
Password:
```

---

# Example Request

Endpoint:

```http
POST /api/query/build
```

Request body:

```json
{
  "select": [
    {
      "entity": "transaction",
      "field": "amount"
    },
    {
      "entity": "party",
      "field": "partyName"
    }
  ],
  "filters": {
    "operator": "AND",
    "conditions": [
      {
        "entity": "transaction",
        "field": "amount",
        "comparator": "greaterThan",
        "value": 1000
      },
      {
        "entity": "party",
        "field": "country",
        "comparator": "equals",
        "value": "CL"
      }
    ]
  },
  "sorting": [
    {
      "entity": "transaction",
      "field": "txnDate",
      "direction": "DESC"
    }
  ],
  "maxResults": 10
}
```

---

# Example Response

```json
{
  "sql": "SELECT t.AMOUNT, p.PARTY_NAME FROM TX_TRANSACTION t LEFT JOIN PARTY p ON t.COUNTERPARTY_ID = p.PARTY_ID WHERE (t.AMOUNT > :p1 AND p.COUNTRY = :p2) ORDER BY t.TXN_DATE DESC LIMIT 10",
  "parameters": {
    "p1": 1000,
    "p2": "CL"
  },
  "resolvedTables": [
    "transaction",
    "party"
  ],
  "resolvedJoins": [
    "party"
  ]
}
```

---

# Testing

The project includes:

* Unit tests
* Integration tests
* Controller endpoint tests
* SQL generation validation tests

Run tests:

```bash
mvn test
```

---

# Design Decisions

## Why Spring JDBC instead of JPA?

This challenge requires dynamic SQL generation at runtime.

Spring JDBC provides:

* Better control over generated SQL
* Simpler dynamic query handling
* Explicit parameter binding
* Lower complexity for metadata-driven queries

---

## Why Metadata-Based Architecture?

Metadata-driven querying allows:

* Dynamic entity resolution
* Configurable joins
* Flexible field definitions
* Easier extensibility

without hardcoding SQL logic.

---

## Why Comparator Strategy Pattern?

Comparator strategies isolate SQL comparison behavior and make the engine extensible.

New comparators can be added without modifying existing builders.

---

# AI Usage Disclosure

AI assistance tools were used during development for:

* Architectural brainstorming
* Refactoring suggestions
* Documentation generation
* Boilerplate acceleration
* Test structure recommendations

All design decisions, implementation validation, debugging, integration, and final code adjustments were reviewed and adapted manually.

---

# Future Improvements

Potential next steps:

* Pagination support
* Multi-root query resolution
* Dialect abstraction
* Query execution caching
* Metadata persistence
* Query optimization strategies
* Authentication / authorization
* Advanced aggregation support

---

# Author

Juan Araos
